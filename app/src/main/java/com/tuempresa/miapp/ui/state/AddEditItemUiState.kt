package com.tuempresa.miapp.ui.state

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
