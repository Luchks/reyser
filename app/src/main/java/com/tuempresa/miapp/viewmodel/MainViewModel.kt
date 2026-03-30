package com.tuempresa.miapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tuempresa.miapp.data.Item
import com.tuempresa.miapp.data.Repository
import com.tuempresa.miapp.ui.state.AddEditItemEvent
import com.tuempresa.miapp.ui.state.AddEditItemUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val repo: Repository) : ViewModel() {

    // ─────────────────────────────────────────────────────────────────────────
    // STATE FLOWS
    // ─────────────────────────────────────────────────────────────────────────

    private val _saving  = MutableStateFlow(false)
    val saving: StateFlow<Boolean> = _saving

    private val _items   = MutableStateFlow<List<Item>>(emptyList())
    val items: StateFlow<List<Item>> = _items

    private val _trash   = MutableStateFlow<List<Item>>(emptyList())
    val trash: StateFlow<List<Item>> = _trash

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    // Estado del formulario de reserva
    private val _addEditState = MutableStateFlow(AddEditItemUiState())
    val addEditState: StateFlow<AddEditItemUiState> = _addEditState.asStateFlow()

    // ─────────────────────────────────────────────────────────────────────────
    // INIT
    // Al arrancar:
    //   1. Observar Room reactivamente (la UI siempre refleja Room)
    //   2. Enviar pendientes si hay internet
    //   3. Bajar datos frescos del servidor
    // ─────────────────────────────────────────────────────────────────────────

    init {
        // Observar Room: cuando Room cambia (por sync o por operación offline),
        // la UI se actualiza automáticamente
        viewModelScope.launch {
            repo.observeItems().collect { _items.value = it }
        }
        viewModelScope.launch {
            repo.observeTrash().collect { _trash.value = it }
        }

        // Al iniciar: primero enviar pendientes, luego bajar datos del servidor
        viewModelScope.launch(Dispatchers.IO) {
            repo.syncPendingOperations() // cola offline → MySQL
            repo.syncItemsFromServer()   // MySQL → Room (activos)
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // POLLING EN TIEMPO REAL (solo activo cuando la app está en foreground)
    // ─────────────────────────────────────────────────────────────────────────

    private var realtimeJob: Job? = null

    fun startRealtimeSync(intervalMs: Long = 5_000L) {
        if (realtimeJob?.isActive == true) return
        realtimeJob = viewModelScope.launch(Dispatchers.IO) {
            Log.d("MainViewModel", "Polling iniciado cada ${intervalMs / 1000}s")
            while (isActive) {
                try {
                    repo.checkForUpdatesAndSync()
                } catch (e: Exception) {
                    Log.w("MainViewModel", "Polling sin conexión: ${e.message}")
                }
                delay(intervalMs)
            }
        }
    }

    fun stopRealtimeSync() {
        realtimeJob?.cancel()
        realtimeJob = null
        Log.d("MainViewModel", "Polling detenido")
    }

    // ─────────────────────────────────────────────────────────────────────────
    // RECONEXIÓN
    // Llamado por NetworkConnectivityObserver cuando vuelve internet.
    // Patrón: primero vaciar cola offline → luego bajar datos frescos
    // ─────────────────────────────────────────────────────────────────────────

    fun syncPendingOnReconnect() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("MainViewModel", "Internet recuperado → sincronizando pendientes")
                repo.syncPendingOperations() // 1. cola offline → MySQL
                repo.syncItemsFromServer()   // 2. MySQL → Room (activos)
                repo.syncTrashFromServer()   // 3. MySQL → Room (papelera)
                Log.d("MainViewModel", "Sync post-reconexión OK")
            } catch (e: Exception) {
                Log.e("MainViewModel", "syncPendingOnReconnect error: ${e.message}")
            }
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // FETCH EXPLÍCITO (pull-to-refresh / LaunchedEffect en pantallas)
    // ─────────────────────────────────────────────────────────────────────────

    fun fetchItems() {
        viewModelScope.launch {
            _loading.value = true
            try {
                withContext(Dispatchers.IO) {
                    repo.syncPendingOperations() // siempre enviar pendientes primero
                    repo.syncItemsFromServer()
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", "fetchItems error: ${e.message}")
                // Sin internet: la UI sigue mostrando Room (último estado conocido)
            } finally {
                _loading.value = false
            }
        }
    }

    fun fetchTrash() {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) { repo.syncTrashFromServer() }
            } catch (e: Exception) {
                Log.e("MainViewModel", "fetchTrash error: ${e.message}")
            }
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // CRUD
    // Patrón: Room primero (respuesta inmediata en UI) → luego servidor
    // ─────────────────────────────────────────────────────────────────────────

    fun deleteItem(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { repo.softDelete(id) }
                .onFailure { Log.e("MainViewModel", "deleteItem error: ${it.message}") }
        }
    }

    fun restoreItem(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { repo.restore(id) }
                .onFailure { Log.e("MainViewModel", "restoreItem error: ${it.message}") }
        }
    }

    /**
     * hardDelete requiere internet. Si falla, avisa al usuario.
     */
    fun hardDeleteItem(id: Int, onError: ((String) -> Unit)? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { repo.hardDelete(id) }
                .onFailure {
                    Log.e("MainViewModel", "hardDeleteItem error: ${it.message}")
                    onError?.invoke("Sin internet. No se puede eliminar definitivamente.")
                }
        }
    }

    fun updateEstadoPago(itemId: Int, nuevoEstado: String) {
        if (itemId <= 0) return
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { repo.updateEstadoPago(itemId, nuevoEstado) }
                .onFailure { Log.e("MainViewModel", "updateEstadoPago error: ${it.message}") }
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // FORMULARIO: cargar item para edición
    // ─────────────────────────────────────────────────────────────────────────

    fun loadItemForEdit(itemId: Int, itemsList: List<Item>) {
        if (itemId == -1) {
            _addEditState.value = AddEditItemUiState(itemId = -1)
            return
        }
        val item = itemsList.find { it.id == itemId } ?: return
        _addEditState.value = AddEditItemUiState(
            itemId               = item.id,
            nombrePrincipal      = item.nombrePrincipal,
            codigoReserva        = item.codigoReserva,
            nombreTour           = item.nombreTour,
            tipoCliente          = item.tipoCliente.ifBlank { "Socio Hotelero" },
            fecha                = item.fecha,
            horaInicio           = item.horaInicio,
            turno                = item.turno.ifBlank { "AM" },
            hotelDireccion       = item.hotelDireccion,
            duracion             = item.duracion,
            pasajerosAdicionales = item.pasajerosAdicionales,
            cantidadPersonas     = item.cantidadPasajero.toIntOrNull() ?: 1,
            pasaporteID          = item.pasaporteID,
            countryCodeWhatsapp  = item.countryCodewhatsapp.ifBlank { "+51" },
            whatsapp             = item.whatsapp,
            correo               = item.correo,
            habitacion           = item.habitacion,
            idioma               = item.idioma,
            pais                 = item.pais,
            tipoPago             = item.tipoPago.ifBlank { "Efectivo" },
            precioPorPersona     = item.precioPorPersona,
            precioTotal          = item.precioTotal,
            precioComisionable   = item.precioComisionable,
            totalComision        = item.totalComision,
            agente               = item.agente,
            countryCodeWaAgente  = item.countryCodewaAgente.ifBlank { "+51" },
            waAgente             = item.waAgente,
            observacion          = item.observacion,
            driver               = item.driver,
            countryCodeWaDriver  = item.countryCodewaDriver.ifBlank { "+51" },
            waDriver             = item.waDriver,
            guia                 = item.guia,
            countryCodeWaGuia    = item.countryCodewaGuia.ifBlank { "+51" },
            waGuia               = item.waGuia,
            tipoServicio         = item.tipoServicio.ifBlank { "Grupal" },
            estadoPago           = item.estadoPago.ifBlank { "PENDIENTE PAGO" },
            comprobantePago      = item.comprobantePago,
            estadoReserva        = item.estadoReserva,
            observacionGeneral   = item.observacionGeneral,
            id_map               = item.id_map,
            cantidadPasajero     = item.cantidadPasajero,
            id_calendar          = item.id_calendar
        )
    }

    // ─────────────────────────────────────────────────────────────────────────
    // FORMULARIO: eventos
    // ─────────────────────────────────────────────────────────────────────────

    fun onEvent(event: AddEditItemEvent) {
        when (event) {
            is AddEditItemEvent.NombreTourChanged       -> updateAndRecalc { it.copy(nombreTour = event.value, duracion = getTourDefaultDuration(event.value)) }
            is AddEditItemEvent.TipoServicioChanged     -> updateAndRecalc { it.copy(tipoServicio = event.value) }
            is AddEditItemEvent.TipoClienteChanged      -> updateAndRecalc { it.copy(tipoCliente = event.value) }
            is AddEditItemEvent.TipoPagoChanged         -> updateAndRecalc { it.copy(tipoPago = event.value) }
            is AddEditItemEvent.CantidadPersonasChanged -> updateAndRecalc { it.copy(cantidadPersonas = event.value) }
            is AddEditItemEvent.FechaChanged            -> _addEditState.update { it.copy(fecha = event.value) }
            is AddEditItemEvent.HoraInicioChanged       -> _addEditState.update { it.copy(horaInicio = event.value) }
            is AddEditItemEvent.TurnoChanged            -> _addEditState.update { it.copy(turno = event.value) }
            is AddEditItemEvent.HotelChanged            -> _addEditState.update { it.copy(hotelDireccion = event.value) }
            is AddEditItemEvent.DuracionChanged         -> _addEditState.update { it.copy(duracion = event.value) }
            is AddEditItemEvent.NombrePrincipalChanged  -> _addEditState.update { it.copy(nombrePrincipal = event.value) }
            is AddEditItemEvent.PasaporteChanged        -> _addEditState.update { it.copy(pasaporteID = event.value) }
            is AddEditItemEvent.CorreoChanged           -> _addEditState.update { it.copy(correo = event.value) }
            is AddEditItemEvent.IdiomaChanged           -> _addEditState.update { it.copy(idioma = event.value) }
            is AddEditItemEvent.PaisChanged             -> _addEditState.update { it.copy(pais = event.value) }
            is AddEditItemEvent.WhatsappCodeChanged     -> _addEditState.update { it.copy(countryCodeWhatsapp = event.value) }
            is AddEditItemEvent.WhatsappNumberChanged   -> _addEditState.update { it.copy(whatsapp = event.value) }
            is AddEditItemEvent.AddPasajero             -> _addEditState.update { it.copy(pasajerosAdicionales = it.pasajerosAdicionales + "") }
            is AddEditItemEvent.PasajeroChanged         -> {
                val list = _addEditState.value.pasajerosAdicionales.toMutableList()
                if (event.index in list.indices) {
                    list[event.index] = event.value
                    _addEditState.update { it.copy(pasajerosAdicionales = list) }
                }
            }
            is AddEditItemEvent.RemovePasajero          -> removePassenger(event.index)
            is AddEditItemEvent.PrecioPorPersonaChanged -> _addEditState.update { it.copy(precioPorPersona = event.value) }
            is AddEditItemEvent.EstadoPagoChanged       -> _addEditState.update { it.copy(estadoPago = event.value) }
            is AddEditItemEvent.ComprobantePagoChanged  -> _addEditState.update { it.copy(comprobantePago = event.value) }
            is AddEditItemEvent.AgenteChanged           -> _addEditState.update { it.copy(agente = event.value) }
            is AddEditItemEvent.WaAgenteCodeChanged     -> _addEditState.update { it.copy(countryCodeWaAgente = event.value) }
            is AddEditItemEvent.WaAgenteNumberChanged   -> _addEditState.update { it.copy(waAgente = event.value) }
            is AddEditItemEvent.DriverChanged           -> _addEditState.update { it.copy(driver = event.value) }
            is AddEditItemEvent.WaDriverCodeChanged     -> _addEditState.update { it.copy(countryCodeWaDriver = event.value) }
            is AddEditItemEvent.WaDriverNumberChanged   -> _addEditState.update { it.copy(waDriver = event.value) }
            is AddEditItemEvent.GuiaChanged             -> _addEditState.update { it.copy(guia = event.value) }
            is AddEditItemEvent.WaGuiaCodeChanged       -> _addEditState.update { it.copy(countryCodeWaGuia = event.value) }
            is AddEditItemEvent.WaGuiaNumberChanged     -> _addEditState.update { it.copy(waGuia = event.value) }
            is AddEditItemEvent.ObservacionChanged      -> _addEditState.update { it.copy(observacion = event.value) }
            is AddEditItemEvent.ObservacionGeneralChanged -> _addEditState.update { it.copy(observacionGeneral = event.value) }
            is AddEditItemEvent.EstadoReservaChanged    -> _addEditState.update { it.copy(estadoReserva = event.estado) }
        }
    }

    fun removePassenger(index: Int) {
        val lista = _addEditState.value.pasajerosAdicionales.toMutableList()
        if (index in lista.indices) {
            lista.removeAt(index)
            _addEditState.update { it.copy(pasajerosAdicionales = lista) }
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // GUARDADO
    // ─────────────────────────────────────────────────────────────────────────

    // Flag para evitar que onPause y onStop disparen dos guardados simultáneos
    private var autoSaveInProgress = false

    /**
     * Autoguardado cuando la app va a background (onPause / onStop).
     * Solo guarda borradores, nunca COMPLETADO.
     *
     * Condición de guarda: el formulario tiene datos reales del usuario,
     * no solo los valores por defecto generados automáticamente.
     */
    fun autoSaveDraft() {
        if (_saving.value) return
        if (autoSaveInProgress) return  // evita doble disparo onPause + onStop
        val s = _addEditState.value

        // CONDICIÓN ROBUSTA: codigoReserva nunca está en blanco (tiene valor por defecto),
        // así que no sirve como guarda. El campo real que indica que el usuario tocó
        // el formulario es nombrePrincipal (no tiene valor por defecto).
        if (s.nombrePrincipal.isBlank()) return
        if (s.estadoReserva == "COMPLETADO") return

        val item = createItemFromState(s, "BORRADOR")
        autoSaveInProgress = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (s.itemId == -1) {
                    val response = repo.createItem(item)
                    response?.body()?.let { body ->
                        if (body.success && body.id != null) {
                            _addEditState.update { it.copy(itemId = body.id) }
                        }
                    }
                } else {
                    repo.updateItem(item)
                }
                Log.d("MainViewModel", "autoSaveDraft OK")
            } catch (e: Exception) {
                Log.w("MainViewModel", "autoSaveDraft quedó como PENDING: ${e.message}")
            } finally {
                autoSaveInProgress = false
            }
        }
    }

    /**
     * Guardado manual del usuario (botón Guardar / Finalizar).
     */
    fun saveItem(finalizar: Boolean = false, onDone: () -> Unit) {
        val estadoFinal = if (finalizar) "COMPLETADO" else _addEditState.value.estadoReserva
        val s = _addEditState.value
        val item = createItemFromState(s, estadoFinal)

        viewModelScope.launch {
            _saving.value = true
            try {
                val response = withContext(Dispatchers.IO) {
                    if (s.itemId == -1) repo.createItem(item)
                    else repo.updateItem(item)
                }
                response?.body()?.id?.let { newId ->
                    _addEditState.update { it.copy(itemId = newId) }
                }
                onDone()
            } catch (e: Exception) {
                Log.e("MainViewModel", "saveItem error: ${e.message}")
                onDone() // igual cerramos el formulario, quedó guardado en Room
            } finally {
                _saving.value = false
            }
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // HELPERS PRIVADOS
    // ─────────────────────────────────────────────────────────────────────────

    private fun createItemFromState(s: AddEditItemUiState, estado: String): Item = Item(
        id                   = if (s.itemId == -1) 0 else s.itemId,
        name                 = s.nombrePrincipal.ifBlank { "Sin Nombre" },
        description          = "Reserva ${s.codigoReserva}",
        codigoReserva        = s.codigoReserva,
        nombreTour           = s.nombreTour,
        tipoCliente          = s.tipoCliente,
        fecha                = s.fecha,
        horaInicio           = s.horaInicio,
        turno                = s.turno,
        hotelDireccion       = s.hotelDireccion,
        duracion             = s.duracion,
        nombrePrincipal      = s.nombrePrincipal,
        pasajerosAdicionales = s.pasajerosAdicionales,
        pasaporteID          = s.pasaporteID,
        countryCodewhatsapp  = s.countryCodeWhatsapp,
        whatsapp             = s.whatsapp,
        correo               = s.correo,
        habitacion           = s.habitacion,
        idioma               = s.idioma,
        pais                 = s.pais,
        tipoPago             = s.tipoPago,
        precioPorPersona     = s.precioPorPersona,
        precioComisionable   = s.precioComisionable,
        precioTotal          = s.precioTotal,
        totalComision        = s.totalComision,
        agente               = s.agente,
        countryCodewaAgente  = s.countryCodeWaAgente,
        waAgente             = s.waAgente,
        observacion          = s.observacion,
        driver               = s.driver,
        countryCodewaDriver  = s.countryCodeWaDriver,
        waDriver             = s.waDriver,
        guia                 = s.guia,
        countryCodewaGuia    = s.countryCodeWaGuia,
        waGuia               = s.waGuia,
        id_calendar          = s.id_calendar,
        id_map               = s.id_map,
        cantidadPasajero     = s.cantidadPersonas.toString(),
        tipoServicio         = s.tipoServicio,
        observacionGeneral   = s.observacionGeneral,
        estadoPago           = s.estadoPago,
        estadoReserva        = estado,
        comprobantePago      = s.comprobantePago,
        FlagActivo           = 1
    )

    private fun updateAndRecalc(transform: (AddEditItemUiState) -> AddEditItemUiState) {
        _addEditState.update { old -> recalcPrices(transform(old)) }
    }

    private fun recalcPrices(state: AddEditItemUiState): AddEditItemUiState {
        if (state.tipoServicio.equals("Privado", ignoreCase = true)) {
            return state.copy(precioPorPersona = 0, precioComisionable = 0, precioTotal = 0)
        }
        val prices = getTourPrices(state.nombreTour)
            ?: return state.copy(precioPorPersona = 0, precioComisionable = 0, precioTotal = 0)
        val pagoConTarjeta = state.tipoPago in listOf("POS", "Pago Link")
        val precioPersona = when (state.tipoCliente) {
            "Cliente Directo" -> if (pagoConTarjeta) prices.publicTarjeta         else prices.publicEfectivo
            "Agencia"         -> if (pagoConTarjeta) prices.agenciaTarjeta         else prices.agenciaEfectivo
            "Socio Hotelero"  -> if (pagoConTarjeta) prices.hotelPasajeroTarjeta   else prices.hotelPasajeroEfectivo
            else              -> 0
        }
        return state.copy(
            precioPorPersona = precioPersona,
            precioTotal      = precioPersona * state.cantidadPersonas
        )
    }

    private data class TourPrices(
        val publicEfectivo: Int, val publicTarjeta: Int,
        val agenciaEfectivo: Int, val agenciaTarjeta: Int,
        val hotelPasajeroEfectivo: Int, val hotelPasajeroTarjeta: Int,
        val hotelComisionEfectivo: Int, val hotelComisionTarjeta: Int
    )

    private fun getTourPrices(nombreTour: String): TourPrices? = when (nombreTour) {
        "LIMA CITY TOUR PLUS"                               -> TourPrices(35, 38, 20, 20, 35, 38, 20, 20)
        "CIRCUITO MÁGICO DEL AGUA"                          -> TourPrices(33, 33, 19, 19, 33, 33, 15, 15)
        "LIMA CITY TOUR PLUS + CIRCUITO MÁGICO DEL AGUA"   -> TourPrices(65, 65, 37, 37, 65, 65, 30, 30)
        "LIMA CITY TOUR CLÁSICO + CATACUMBAS"              -> TourPrices(40, 40, 25, 25, 40, 40, 20, 20)
        "FULL DAY ICA: PARACAS Y HUACACHINA"               -> TourPrices(99, 99, 75, 75, 99, 99, 20, 20)
        else -> null
    }

    private fun getTourDefaultDuration(nombreTour: String): String = when (nombreTour) {
        "LIMA CITY TOUR PLUS", "LIMA CITY TOUR CLÁSICO + CATACUMBAS" -> "4 Horas"
        "CIRCUITO MÁGICO DEL AGUA"                                    -> "3 Horas"
        "LIMA CITY TOUR PLUS + CIRCUITO MÁGICO DEL AGUA"             -> "7 Horas"
        "FULL DAY ICA: PARACAS Y HUACACHINA"                         -> "18 Horas"
        else                                                          -> "4 Horas"
    }

    // ─────────────────────────────────────────────────────────────────────────
    // LIFECYCLE
    // ─────────────────────────────────────────────────────────────────────────

    override fun onCleared() {
        stopRealtimeSync()
        super.onCleared()

        val s = _addEditState.value
        // Misma condición robusta que autoSaveDraft: solo guardar si el usuario
        // realmente escribió algo (nombrePrincipal es el único campo sin valor por defecto)
        if (s.nombrePrincipal.isBlank()) return
        if (s.estadoReserva == "COMPLETADO") return

        val item = createItemFromState(s, "BORRADOR")
        kotlinx.coroutines.runBlocking(Dispatchers.IO) {
            runCatching { repo.saveDraftToRoom(item, s.itemId) }
                .onSuccess { Log.d("MainViewModel", "onCleared: borrador guardado en Room") }
                .onFailure { Log.e("MainViewModel", "onCleared error: ${it.message}") }
        }
    }
}
