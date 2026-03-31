package com.tuempresa.miapp.ui.state

import com.tuempresa.miapp.data.Item

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class AddEditItemUiState(
    val itemId: Int = -1,
    val nombrePrincipal: String = "",
    val codigoReserva: String = "LVT - ${System.currentTimeMillis().toString().takeLast(6)}",
    val nombreTour: String = "",
    val tipoCliente: String = "Socio Hotelero",
    val fecha: String = LocalDate.now().toString(),
    val horaInicio: String = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a")),
    val turno: String = "AM",
    val hotelDireccion: String = "",
    val duracion: String = "4 horas",
    val pasajerosAdicionales: List<String> = emptyList(),
    val pasaporteID: String = "",
    val countryCodeWhatsapp: String = "+51",
    val whatsapp: String = "",
    val correo: String = "",
    val habitacion: String = "201",
    val idioma: String = "",
    val pais: String = "",
    val tipoPago: String = "POS",
    val precioPorPersona: Int = 0,
    val precioTotal: Int = 0,
    val precioComisionable: Int = 20,
    val totalComision: Int = 0,
    val cantidadPersonas: Int = 1,
    val agente: String = "",
    val countryCodeWaAgente: String = "+51",
    val waAgente: String = "",
    val observacion: String = "",
    val driver: String = "",
    val countryCodeWaDriver: String = "+51",
    val waDriver: String = "",
    val guia: String = "",
    val countryCodeWaGuia: String = "+51",
    val waGuia: String = "",
    val tipoServicio: String = "Grupal",
    val observacionGeneral: String = "",
    val estadoPago: String = "PENDIENTE PAGO",
    val comprobantePago: String = "NO APLICA",
    val estadoReserva: String = "BORRADOR",
    val id_map: String = "",
    val cantidadPasajero: String = "1",

    // ✅ FIX CRÍTICO: campo necesario para preservar el ID real de Google Calendar.
    // Sin esto, createItemFromState sobreescribía id_calendar con el itemId local,
    // destruyendo el enlace al evento de Calendar en cada actualización.
    val id_calendar: String = ""
)

/**
 * Convierte el estado del formulario en un Item para previsualización inmediata.
 * Se usa en el modo VIEW cuando el item aún no llegó a Room tras un guardado optimista.
 * El id puede ser 0 si es una reserva nueva sin respuesta del servidor aún.
 */
fun AddEditItemUiState.toPreviewItem(): Item = Item(
    id                   = if (itemId == -1) 0 else itemId,
    name                 = nombrePrincipal.ifBlank { "Sin Nombre" },
    description          = "Reserva $codigoReserva",
    codigoReserva        = codigoReserva,
    nombreTour           = nombreTour,
    tipoCliente          = tipoCliente,
    fecha                = fecha,
    horaInicio           = horaInicio,
    turno                = turno,
    hotelDireccion       = hotelDireccion,
    duracion             = duracion,
    nombrePrincipal      = nombrePrincipal,
    pasajerosAdicionales = pasajerosAdicionales,
    pasaporteID          = pasaporteID,
    countryCodewhatsapp  = countryCodeWhatsapp,
    whatsapp             = whatsapp,
    correo               = correo,
    habitacion           = habitacion,
    idioma               = idioma,
    pais                 = pais,
    tipoPago             = tipoPago,
    precioPorPersona     = precioPorPersona,
    precioTotal          = precioTotal,
    precioComisionable   = precioComisionable,
    totalComision        = totalComision,
    agente               = agente,
    countryCodewaAgente  = countryCodeWaAgente,
    waAgente             = waAgente,
    observacion          = observacion,
    driver               = driver,
    countryCodewaDriver  = countryCodeWaDriver,
    waDriver             = waDriver,
    guia                 = guia,
    countryCodewaGuia    = countryCodeWaGuia,
    waGuia               = waGuia,
    id_calendar          = id_calendar,
    id_map               = id_map,
    cantidadPasajero     = cantidadPersonas.toString(),
    tipoServicio         = tipoServicio,
    observacionGeneral   = observacionGeneral,
    estadoPago           = estadoPago,
    estadoReserva        = estadoReserva,
    comprobantePago      = comprobantePago,
    FlagActivo           = 1
)
