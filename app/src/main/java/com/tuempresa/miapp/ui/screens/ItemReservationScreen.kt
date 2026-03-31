package com.tuempresa.miapp.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tuempresa.miapp.data.Item
import com.tuempresa.miapp.ui.components.*
import com.tuempresa.miapp.ui.state.AddEditItemEvent
import com.tuempresa.miapp.ui.state.toPreviewItem
import com.tuempresa.miapp.viewmodel.MainViewModel
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight

// ------------------------------------------------------------
//  MODO DE PANTALLA
// ------------------------------------------------------------
enum class ReservationMode { VIEW, EDIT }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemReservationScreen(
    navController: NavController,
    itemId: Int,
    vm: MainViewModel
) {
    var mode by remember { mutableStateOf(if (itemId == -1) ReservationMode.EDIT else ReservationMode.VIEW) }

    val items by vm.items.collectAsState()
    val _items = vm.items           // referencia al StateFlow para leer en LaunchedEffect
    val state by vm.addEditState.collectAsState()
    val saving by vm.saving.collectAsState()

    // Función que ejecuta el guardado silencioso y luego navega atrás
    val saveAndGoBack: () -> Unit = {
        if (mode == ReservationMode.EDIT) vm.autoSaveDraft()
        navController.popBackStack()
    }

    // Interceptar botón físico/gesto de "Atrás" del sistema
    BackHandler { saveAndGoBack() }

    // Cargar datos solo una vez al abrir la pantalla (key = itemId).
    // NO usar "items" como key: Room actualiza "items" frecuentemente
    // y provocaría que loadItemForEdit se llame de nuevo, pisando los
    // cambios que el usuario esté escribiendo en ese momento.
    LaunchedEffect(itemId) {
        if (_items.value.isEmpty()) vm.fetchItems()
        vm.loadItemForEdit(itemId, vm.items.value)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (mode == ReservationMode.VIEW) "Detalle de Reserva" else "Editar Reserva")
                },
                navigationIcon = {
                    IconButton(onClick = saveAndGoBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
                when (mode) {
                ReservationMode.VIEW -> {
                    // Buscar el item por serverId primero, luego por roomId
                    // Para reservas nuevas guardadas offline, el id puede ser 0
                    // hasta que el servidor responda — mostramos el state local como fallback
                    val effectiveId = if (itemId == -1) state.itemId else itemId
                    val item = items.find { it.id == effectiveId }

                    when {
                        item != null -> {
                            // Caso normal: item encontrado en la lista (viene de Room/servidor)
                            ReservationReport(
                                item = item,
                                onEdit = { mode = ReservationMode.EDIT }
                            )
                        }
                        state.nombrePrincipal.isNotBlank() -> {
                            // Guardado optimista: el item aún no llegó a items (Room aún
                            // está procesando), pero tenemos el estado local — lo mostramos
                            // directamente desde el state del formulario sin esperar a Room
                            ReservationReport(
                                item = state.toPreviewItem(),
                                onEdit = { mode = ReservationMode.EDIT }
                            )
                        }
                        itemId == -1 -> {
                            // Reserva nueva sin datos — volver al listado
                            LaunchedEffect(Unit) { navController.popBackStack() }
                        }
                        else -> {
                            // Cargando item existente del servidor
                            CircularProgressIndicator(Modifier.align(Alignment.Center))
                        }
                    }
                }

                ReservationMode.EDIT -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(12.dp)
                    ) {
                        // ---------------- DATOS RESERVA ----------------
                        ReservationSection(
                            codigoReserva = state.codigoReserva,
                            nombreTour = state.nombreTour,
                            tipoServicio = state.tipoServicio,
                            tipoCliente = state.tipoCliente,
                            agente = state.agente,
                            waAgente = state.waAgente,
                            fecha = state.fecha,
                            turno = state.turno,
                            horaInicio = state.horaInicio,
                            duracion = state.duracion,
                            hotelDireccion = state.hotelDireccion,
                            ontipoServicioChange = { vm.onEvent(AddEditItemEvent.TipoServicioChanged(it)) },
                            onAgenteChange = { vm.onEvent(AddEditItemEvent.AgenteChanged(it)) },
                            onWaAgenteChange = { vm.onEvent(AddEditItemEvent.WaAgenteNumberChanged(it)) },
                            onNombreTourChange = { vm.onEvent(AddEditItemEvent.NombreTourChanged(it)) },
                            onTipoClienteChange = { vm.onEvent(AddEditItemEvent.TipoClienteChanged(it)) },
                            onFechaChange = { vm.onEvent(AddEditItemEvent.FechaChanged(it)) },
                            onHoraChange = { vm.onEvent(AddEditItemEvent.HoraInicioChanged(it)) },
                            onTurnoChange = { vm.onEvent(AddEditItemEvent.TurnoChanged(it)) },
                            onHotelChange = { vm.onEvent(AddEditItemEvent.HotelChanged(it)) },
                            onDuracionChange = { vm.onEvent(AddEditItemEvent.DuracionChanged(it)) }
                        )

                        Spacer(Modifier.height(12.dp))

                        // ---------------- PASAJEROS ----------------
                        PassengerSection(
                            nombrePrincipal = state.nombrePrincipal,
                            pasaporteID = state.pasaporteID,
                            correo = state.correo,
                            idioma = state.idioma,
                            pais = state.pais,
                            passengers = state.pasajerosAdicionales,
                            cantidadPersonas = state.cantidadPersonas,
                            onCantidadChange = { vm.onEvent(AddEditItemEvent.CantidadPersonasChanged(it)) },
                            onAddPassenger = { vm.onEvent(AddEditItemEvent.AddPasajero) },
                            onPassengerChange = { i, v -> vm.onEvent(AddEditItemEvent.PasajeroChanged(i, v)) },
                            onRemovePassenger = { vm.removePassenger(it) },
                            onNombreChange = { vm.onEvent(AddEditItemEvent.NombrePrincipalChanged(it)) },
                            onPasaporteChange = { vm.onEvent(AddEditItemEvent.PasaporteChanged(it)) },
                            onCorreoChange = { vm.onEvent(AddEditItemEvent.CorreoChanged(it)) },
                            onIdiomaChange = { vm.onEvent(AddEditItemEvent.IdiomaChanged(it)) },
                            onPaisChange = { vm.onEvent(AddEditItemEvent.PaisChanged(it)) },
                            countryCodewhatsapp = state.countryCodeWhatsapp,
                            whatsapp = state.whatsapp,
                            onWhatsappCountryChange = { vm.onEvent(AddEditItemEvent.WhatsappCodeChanged(it)) },
                            onWhatsappChange = { vm.onEvent(AddEditItemEvent.WhatsappNumberChanged(it)) }
                        )

                        Spacer(Modifier.height(12.dp))

                        // ---------------- PAGO ----------------
                        PaymentSection(
                            tipoServicio = state.tipoServicio,
                            precioPorPersona = state.precioPorPersona,
                            precioTotal = state.precioTotal,
                            tipoPago = state.tipoPago,
                            estadoPago = state.estadoPago,
                            comprobantePago = state.comprobantePago,
                            onTipoPagoChange = { vm.onEvent(AddEditItemEvent.TipoPagoChanged(it)) },
                            onPrecioPorPersonaChange = { vm.onEvent(AddEditItemEvent.PrecioPorPersonaChanged(it)) },
                            onEstadoPagoChange = { vm.onEvent(AddEditItemEvent.EstadoPagoChanged(it)) },
                            onComprobantePagoChange = { vm.onEvent(AddEditItemEvent.ComprobantePagoChanged(it)) }
                        )

                        Spacer(Modifier.height(12.dp))

                        // ---------------- PERSONAL ----------------
                        StaffSection(
                            driver = state.driver,
                            guia = state.guia,
                            observacion = state.observacion,
                            observacionGeneral = state.observacionGeneral,
                            countryCodewaAgente = state.countryCodeWaAgente,
                            countryCodewaDriver = state.countryCodeWaDriver,
                            waDriver = state.waDriver,
                            countryCodewaGuia = state.countryCodeWaGuia,
                            waGuia = state.waGuia,
                            onDriverChange = { vm.onEvent(AddEditItemEvent.DriverChanged(it)) },
                            onGuiaChange = { vm.onEvent(AddEditItemEvent.GuiaChanged(it)) },
                            onObservacionChange = { vm.onEvent(AddEditItemEvent.ObservacionChanged(it)) },
                            onWaAgenteCountry = { vm.onEvent(AddEditItemEvent.WaAgenteCodeChanged(it)) },
                            onWaDriverCountry = { vm.onEvent(AddEditItemEvent.WaDriverCodeChanged(it)) },
                            onWaDriverChange = { vm.onEvent(AddEditItemEvent.WaDriverNumberChanged(it)) },
                            onWaGuiaCountry = { vm.onEvent(AddEditItemEvent.WaGuiaCodeChanged(it)) },
                            onWaGuiaChange = { vm.onEvent(AddEditItemEvent.WaGuiaNumberChanged(it)) },
                            onObservacionGeneralChange = { vm.onEvent(AddEditItemEvent.ObservacionGeneralChanged(it)) }
                        )

                        Spacer(Modifier.height(20.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            OutlinedButton(
                                onClick = {
                                    if (itemId == -1) navController.popBackStack()
                                    else mode = ReservationMode.VIEW
                                },
                                modifier = Modifier.weight(1f)
                            ) { Text("Cancelar") }

                            Button(
                                onClick = {
                                    vm.saveItem(finalizar = true) {
                                        mode = ReservationMode.VIEW
                                    }
                                },
                                modifier = Modifier.weight(1f),
                                enabled = !saving
                            ) { Text(if (saving) "Guardando..." else "Guardar") }
                        }
                    }
                }
            }
        }
    }
}

// ------------------------------------------------------------
//  VISTA SOLO LECTURA
// ------------------------------------------------------------

@Composable
private fun ReservationReport(
    item: Item,
    onEdit: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp) // Más espacio entre secciones
    ) {
        // Cabecera con el Código de Reserva resaltado
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.ConfirmationNumber, contentDescription = null)
                Spacer(Modifier.width(12.dp))
                Column {
                    Text("CÓDIGO DE RESERVA", style = MaterialTheme.typography.labelSmall)
                    Text(item.codigoReserva, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                }
            }
        }

        // --- SECCIÓN: DATOS DE LA RESERVA ---
        InfoSection(title = "DATOS DE LA RESERVA", icon = Icons.Default.Info) {
            InfoRow(Icons.Default.Tour, "Servicio", item.nombreTour)
            InfoRow(Icons.Default.Category, "Tipo", "${item.tipoServicio} (${item.tipoCliente})")
            InfoRow(Icons.Default.Event, "Fecha y Turno", "${item.fecha} - ${item.turno}")
            InfoRow(Icons.Default.Schedule, "Inicio/Duración", "${item.horaInicio} (${item.duracion})")
            InfoRow(Icons.Default.LocationOn, "Hotel/Dirección", item.hotelDireccion)
            InfoRow(Icons.Default.SupportAgent, "Agente", "${item.agente} / ${item.waAgente}")
        }

        // --- SECCIÓN: PASAJEROS ---
        InfoSection(title = "PASAJEROS", icon = Icons.Default.Groups) {
            InfoRow(Icons.Default.Person, "Principal", item.nombrePrincipal)
            InfoRow(Icons.Default.Badge, "Pasaporte", item.pasaporteID)
            InfoRow(Icons.Default.Phone, "WhatsApp", "${item.countryCodewhatsapp}-${item.whatsapp}")
            InfoRow(Icons.Default.Public, "Nacionalidad", item.pais)
            InfoRow(Icons.Default.Translate, "Idioma", item.idioma)
            
            // CAMBIO AQUÍ: Usamos joinToString para convertir la lista en un solo String
            if (item.pasajerosAdicionales.isNotEmpty()) {
                InfoRow(
                    icon = Icons.Default.List, 
                    label = "Adicionales", 
                    value = item.pasajerosAdicionales.joinToString("\n")
                    )
                }
        } 
          
        // --- SECCIÓN: PAGO ---
        InfoSection(title = "PAGO", icon = Icons.Default.Payments) {
            InfoRow(Icons.Default.AirlineSeatReclineNormal, "pasajeros", item.cantidadPasajero)
            InfoRow(Icons.Default.AttachMoney, "Precio", item.precioPorPersona.toString())
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column(Modifier.weight(1f)) {
                    Text("Total", style = MaterialTheme.typography.labelMedium)
                    Text("${item.precioTotal}", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                }
                Column(Modifier.weight(1f)) {
                    Text("Estado", style = MaterialTheme.typography.labelMedium)
                    SuggestionChip(onClick = {}, label = { Text(item.estadoPago) }, enabled = false)
                }
            }
            InfoRow(Icons.Default.CreditCard, "Método", item.tipoPago)
        }

        // --- SECCIÓN: PERSONAL ---
        InfoSection(title = "PERSONAL ASIGNADO", icon = Icons.Default.AssignmentInd) {
            InfoRow(Icons.Default.DirectionsCar, "Driver", "${item.driver} (${item.countryCodewaDriver}-${item.waDriver})")
            InfoRow(Icons.Default.RecordVoiceOver, "Guía", "${item.guia} (${item.countryCodewaGuia}-${item.waGuia})")
        }

        // --- OBSERVACIONES (Si existen) ---
        if (item.observacion.isNotBlank() || item.observacionGeneral.isNotBlank()) {
            InfoSection(title = "OBSERVACIONES", icon = Icons.Default.Notes) {
                if (item.observacion.isNotBlank()) Text("Internas: ${item.observacion}", style = MaterialTheme.typography.bodyMedium)
                if (item.observacionGeneral.isNotBlank()) Text("Generales: ${item.observacionGeneral}", style = MaterialTheme.typography.bodyMedium)
            }
        }

        Spacer(Modifier.height(8.dp))

        Button(
            onClick = onEdit,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Icon(Icons.Default.Edit, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("EDITAR RESERVA", fontWeight = FontWeight.Bold)
        }
    }
}

// --- COMPONENTES AUXILIARES PARA MANTENER EL CÓDIGO LIMPIO ---

@Composable
private fun InfoSection(title: String, icon: ImageVector, content: @Composable ColumnScope.() -> Unit) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text(text = title, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            }
            HorizontalDivider(thickness = 0.5.dp, color = MaterialTheme.colorScheme.outlineVariant)
            content()
        }
    }
}

@Composable
private fun InfoRow(icon: ImageVector, label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(16.dp).padding(top = 2.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.width(12.dp))
        Column {
            Text(text = label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(text = value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
        }
    }
}
