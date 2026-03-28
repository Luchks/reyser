package com.tuempresa.miapp.ui.state

sealed interface AddEditItemEvent {
    // Sección Reserva
    data class NombreTourChanged(val value: String) : AddEditItemEvent
    data class TipoServicioChanged(val value: String) : AddEditItemEvent
    data class TipoClienteChanged(val value: String) : AddEditItemEvent
    data class FechaChanged(val value: String) : AddEditItemEvent
    data class HoraInicioChanged(val value: String) : AddEditItemEvent
    data class TurnoChanged(val value: String) : AddEditItemEvent
    data class HotelChanged(val value: String) : AddEditItemEvent
    data class DuracionChanged(val value: String) : AddEditItemEvent

    // Sección Pasajeros
    data class NombrePrincipalChanged(val value: String) : AddEditItemEvent
    data class PasaporteChanged(val value: String) : AddEditItemEvent
    data class CorreoChanged(val value: String) : AddEditItemEvent
    data class IdiomaChanged(val value: String) : AddEditItemEvent
    data class PaisChanged(val value: String) : AddEditItemEvent
    data class CantidadPersonasChanged(val value: Int) : AddEditItemEvent
    data class WhatsappCodeChanged(val value: String) : AddEditItemEvent
    data class WhatsappNumberChanged(val value: String) : AddEditItemEvent
    
    // Lista Dinámica de Pasajeros
    object AddPasajero : AddEditItemEvent
    data class PasajeroChanged(val index: Int, val value: String) : AddEditItemEvent
    data class RemovePasajero(val index: Int) : AddEditItemEvent

    // Sección Pagos
    data class PrecioPorPersonaChanged(val value: Int) : AddEditItemEvent
    data class TipoPagoChanged(val value: String) : AddEditItemEvent
    data class EstadoPagoChanged(val value: String) : AddEditItemEvent
    data class ComprobantePagoChanged(val value: String) : AddEditItemEvent

    // Sección Staff (Agente, Driver, Guía)
    data class AgenteChanged(val value: String) : AddEditItemEvent
    data class WaAgenteCodeChanged(val value: String) : AddEditItemEvent
    data class WaAgenteNumberChanged(val value: String) : AddEditItemEvent
    
    data class DriverChanged(val value: String) : AddEditItemEvent
    data class WaDriverCodeChanged(val value: String) : AddEditItemEvent
    data class WaDriverNumberChanged(val value: String) : AddEditItemEvent
    
    data class GuiaChanged(val value: String) : AddEditItemEvent
    data class WaGuiaCodeChanged(val value: String) : AddEditItemEvent
    data class WaGuiaNumberChanged(val value: String) : AddEditItemEvent

    // Observaciones y Control
    data class ObservacionChanged(val value: String) : AddEditItemEvent
    data class ObservacionGeneralChanged(val value: String) : AddEditItemEvent
    data class EstadoReservaChanged(val estado: String) : AddEditItemEvent
}