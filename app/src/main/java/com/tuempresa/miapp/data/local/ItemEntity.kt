package com.tuempresa.miapp.data.local

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.tuempresa.miapp.data.Item

//**********************************************************
@Entity(
    tableName = "items",
    indices = [Index(value = ["serverId"], unique = true)]
)
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val roomId: Int = 0,

    // ── Sync metadata ────────────────────────────────────────────────────
    val serverId: Int = 0,
    val syncStatus: String = SyncStatus.SYNCED.name,
    val flagActivo: Int = 1,

    // ── Campos de Item ───────────────────────────────────────────────────
    val name: String = "",
    val description: String = "",
    val codigoReserva: String = "",
    val nombreTour: String = "",
    val tipoCliente: String = "",
    val fecha: String = "",
    val horaInicio: String = "",
    val turno: String = "",
    val hotelDireccion: String = "",
    val duracion: String = "",
    val nombrePrincipal: String = "",
    val pasajerosAdicionales: List<String> = emptyList(),   // → TypeConverter
    val pasaporteID: String = "",
    val countryCodewhatsapp: String = "",
    val whatsapp: String = "",
    val correo: String = "",
    val habitacion: String = "",
    val idioma: String = "",
    val pais: String = "",
    val tipoPago: String = "",
    val precioPorPersona: Int = 0,
    val precioTotal: Int = 0,
    val precioComisionable: Int = 0,
    val totalComision: Int = 0,
    val agente: String = "",
    val countryCodewaAgente: String = "",
    val waAgente: String = "",
    val observacion: String = "",
    val driver: String = "",
    val countryCodewaDriver: String = "",
    val waDriver: String = "",
    val guia: String = "",
    val countryCodewaGuia: String = "",
    val waGuia: String = "",
    val id_calendar: String = "",
    val id_map: String = "",
    val cantidadPasajero: String = "",
    val tipoServicio: String = "",
    val observacionGeneral: String = "",
    val estadoPago: String = "",
    val estadoReserva: String = "BORRADOR",
    val comprobantePago: String = "NO APLICA"
) {
    // ── Conversión a modelo de dominio ───────────────────────────────────

    fun toItem(): Item = Item(
        id                  = serverId,
        name                = name,
        description         = description,
        codigoReserva       = codigoReserva,
        nombreTour          = nombreTour,
        tipoCliente         = tipoCliente,
        fecha               = fecha,
        horaInicio          = horaInicio,
        turno               = turno,
        hotelDireccion      = hotelDireccion,
        duracion            = duracion,
        nombrePrincipal     = nombrePrincipal,
        pasajerosAdicionales = pasajerosAdicionales,
        pasaporteID         = pasaporteID,
        countryCodewhatsapp = countryCodewhatsapp,
        whatsapp            = whatsapp,
        correo              = correo,
        habitacion          = habitacion,
        idioma              = idioma,
        pais                = pais,
        tipoPago            = tipoPago,
        precioPorPersona    = precioPorPersona,
        precioTotal         = precioTotal,
        precioComisionable  = precioComisionable,
        totalComision       = totalComision,
        agente              = agente,
        countryCodewaAgente = countryCodewaAgente,
        waAgente            = waAgente,
        observacion         = observacion,
        driver              = driver,
        countryCodewaDriver = countryCodewaDriver,
        waDriver            = waDriver,
        guia                = guia,
        countryCodewaGuia   = countryCodewaGuia,
        waGuia              = waGuia,
        id_calendar         = id_calendar,
        id_map              = id_map,
        cantidadPasajero    = cantidadPasajero,
        tipoServicio        = tipoServicio,
        observacionGeneral  = observacionGeneral,
        estadoPago          = estadoPago,
        estadoReserva       = estadoReserva,
        comprobantePago     = comprobantePago,
        FlagActivo          = flagActivo
    )

    companion object {
        /** Construye un [ItemEntity] desde un [Item] del servidor. */
        fun fromItem(
            item: Item,
            syncStatus: SyncStatus = SyncStatus.SYNCED,
            roomId: Int = 0          // 0 = nuevo insert; >0 = actualizar fila existente
        ): ItemEntity = ItemEntity(
            roomId              = roomId,
            serverId            = item.id,
            syncStatus          = syncStatus.name,
            flagActivo          = item.FlagActivo,
            name                = item.name,
            description         = item.description,
            codigoReserva       = item.codigoReserva,
            nombreTour          = item.nombreTour,
            tipoCliente         = item.tipoCliente,
            fecha               = item.fecha,
            horaInicio          = item.horaInicio,
            turno               = item.turno,
            hotelDireccion      = item.hotelDireccion,
            duracion            = item.duracion,
            nombrePrincipal     = item.nombrePrincipal,
            pasajerosAdicionales = item.pasajerosAdicionales,
            pasaporteID         = item.pasaporteID,
            countryCodewhatsapp = item.countryCodewhatsapp,
            whatsapp            = item.whatsapp,
            correo              = item.correo,
            habitacion          = item.habitacion,
            idioma              = item.idioma,
            pais                = item.pais,
            tipoPago            = item.tipoPago,
            precioPorPersona    = item.precioPorPersona,
            precioTotal         = item.precioTotal,
            precioComisionable  = item.precioComisionable,
            totalComision       = item.totalComision,
            agente              = item.agente,
            countryCodewaAgente = item.countryCodewaAgente,
            waAgente            = item.waAgente,
            observacion         = item.observacion,
            driver              = item.driver,
            countryCodewaDriver = item.countryCodewaDriver,
            waDriver            = item.waDriver,
            guia                = item.guia,
            countryCodewaGuia   = item.countryCodewaGuia,
            waGuia              = item.waGuia,
            id_calendar         = item.id_calendar,
            id_map              = item.id_map,
            cantidadPasajero    = item.cantidadPasajero,
            tipoServicio        = item.tipoServicio,
            observacionGeneral  = item.observacionGeneral,
            estadoPago          = item.estadoPago,
            estadoReserva       = item.estadoReserva,
            comprobantePago     = item.comprobantePago
        )
    }
}
