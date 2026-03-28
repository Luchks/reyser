package com.tuempresa.miapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.IconButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

// -------------------------------------------------------------
//  DATOS DE LA RESERVA
// -------------------------------------------------------------
@Composable
fun ReservationSection(
    codigoReserva: String,
    nombreTour: String,
    tipoServicio: String,
    tipoCliente: String,
    agente: String,
    waAgente: String,
    fecha: String,
    turno: String,
    horaInicio: String,
    duracion: String,
    hotelDireccion: String,
    ontipoServicioChange: (String) -> Unit,
    onAgenteChange: (String) -> Unit,
    onWaAgenteChange: (String) -> Unit,
    onNombreTourChange: (String) -> Unit,
    onTipoClienteChange: (String) -> Unit,
    onFechaChange: (String) -> Unit,
    onHoraChange: (String) -> Unit,
    onTurnoChange: (String) -> Unit,
    onHotelChange: (String) -> Unit,
    onDuracionChange: (String) -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Código de Reserva:",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Medium
        )

        Spacer(Modifier.width(8.dp))

        Text(
            text = codigoReserva,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
    }

    Section("Datos de la Reserva") {

        // Nombre del servicio
        Spacer(Modifier.height(8.dp))
        ComboBox(
            label = "Nombre del Servicio Asignado",
            options = listOf(
                "LIMA CITY TOUR PLUS",
                "CIRCUITO MÁGICO DEL AGUA",
                "LIMA CITY TOUR PLUS + CIRCUITO MÁGICO DEL AGUA",
                "LIMA CITY TOUR CLÁSICO + CATACUMBAS",
                "FULL DAY ICA: PARACAS Y HUACACHINA",
                "OTRO (editable)"
            ),
            selected = nombreTour,
            onSelect = onNombreTourChange,
            editableWhen = { it == "OTRO (editable)" },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            ComboBox(
                label = "Tipo de Servicio",
                options = listOf("Grupal", "Privado"),
                selected = tipoServicio,
                onSelect = ontipoServicioChange,
                modifier = Modifier.weight(1f)
            )

            ComboBox(
                label = "Tipo de Cliente",
                options = listOf("Agencia", "Socio Hotelero", "Cliente Directo"),
                selected = tipoCliente,
                onSelect = onTipoClienteChange,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(8.dp))
            // ... (dentro de ReservationSection)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // CAMPO 1: AGENTE (CORREGIDO: Eliminamos el 'remember' innecesario)
                OutlinedTextField(
                    value = agente,
                    onValueChange = { text ->
                        onAgenteChange(text) // Llama al evento correcto
                    },
                    label = { Text("Contacto o Agente") },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("") }
                )

    // CAMPO 2: TELÉFONO (CORREGIDO: El error estaba aquí)
    OutlinedTextField(
        value = waAgente,
        onValueChange = { text ->
            onWaAgenteChange(text) // <--- ANTES DECÍA onAgenteChange, por eso fallaba
        },
        label = { Text("Teléfono del contacto") },
        modifier = Modifier.weight(1f),
        placeholder = { Text("") }
    )
}
    
        DatePickerField(
            label = "Fecha",
            value = fecha,
            onValueChange = onFechaChange
        )

        Spacer(Modifier.height(12.dp))

        // Turno
        Text("Turno", fontWeight = FontWeight.Bold)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TurnoRadioOption(
                label = "AM",
                value = "AM",
                selectedTurno = turno,
                onTurnoChange = onTurnoChange
            )

            Spacer(Modifier.width(24.dp))

            TurnoRadioOption(
                label = "PM",
                value = "PM",
                selectedTurno = turno,
                onTurnoChange = onTurnoChange
            )
        }

        Spacer(Modifier.height(12.dp))

        HotelPlaceField(
            value = hotelDireccion,
            onValueChange = onHotelChange,
            label = "Hotel / Dirección"
        )

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            TimePickerField(
                label = "Hora de Inicio",
                value = horaInicio,
                onValueChange = onHoraChange,
                modifier = Modifier.weight(1f)
            )

            OutlinedTextField(
                value = duracion,
                onValueChange = onDuracionChange,
                label = { Text("Duración") },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun TurnoRadioOption(
    label: String,
    value: String,
    selectedTurno: String,
    onTurnoChange: (String) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            selected = selectedTurno == value,
            onClick = { onTurnoChange(value) }
        )
        Text(text = label)
    }
}

@Composable
fun PassengerSection(
    nombrePrincipal: String,
    pasaporteID: String,
    correo: String,
    idioma: String,
    pais: String,
    passengers: List<String>,
    cantidadPersonas: Int,
    onCantidadChange: (Int) -> Unit,
    onAddPassenger: () -> Unit,
    onPassengerChange: (Int, String) -> Unit,

    // 🔥 CORREGIDO
    onRemovePassenger: (Int) -> Unit,

    onNombreChange: (String) -> Unit,
    onPasaporteChange: (String) -> Unit,
    onCorreoChange: (String) -> Unit,
    onIdiomaChange: (String) -> Unit,
    onPaisChange: (String) -> Unit,
    countryCodewhatsapp: String,
    whatsapp: String,
    onWhatsappCountryChange: (String) -> Unit,
    onWhatsappChange: (String) -> Unit
) {
    Section("Datos del Pasajero") {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            OutlinedTextField(
                value = cantidadPersonas.toString(),
                onValueChange = { text ->
                    val clean = text.filter { it.isDigit() }
                    val nuevoValor = clean.toIntOrNull()?.coerceAtLeast(1) ?: 1
                    onCantidadChange(nuevoValor)
                },
                label = { Text("Cant.") },
                readOnly = false,
                modifier = Modifier.weight(0.35f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                leadingIcon = {
                    IconButton(onClick = {
                        val nuevo = (cantidadPersonas - 1).coerceAtLeast(1)
                        onCantidadChange(nuevo)
                    }) {
                        Icon(Icons.Filled.Remove, contentDescription = "Disminuir")
                    }
                },
                trailingIcon = {
                    IconButton(onClick = {
                        val nuevo = cantidadPersonas + 1
                        onCantidadChange(nuevo)
                    }) {
                        Icon(Icons.Filled.Add, contentDescription = "Aumentar")
                    }
                }
            )

            Spacer(Modifier.width(8.dp))

            OutlinedTextField(
                value = nombrePrincipal,
                onValueChange = onNombreChange,
                label = { Text("Pasajero Principal (*)") },
                modifier = Modifier.weight(0.60f)
            )
        }

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = pasaporteID,
            onValueChange = onPasaporteChange,
            label = { Text("Pasaporte o ID") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        WhatsAppInput(
            modifier = Modifier.fillMaxWidth(),
            currentCountryCode = countryCodewhatsapp,
            currentPhoneNumber = whatsapp,
            onCountryCodeChange = onWhatsappCountryChange,
            onPhoneNumberChange = onWhatsappChange
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = correo,
            onValueChange = onCorreoChange,
            label = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = pais,
                onValueChange = onPaisChange,
                label = { Text("Nacionalidad") },
                modifier = Modifier.weight(1f)
            )

            OutlinedTextField(
                value = idioma,
                onValueChange = onIdiomaChange,
                label = { Text("Idioma") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(8.dp))

        passengers.forEachIndexed { index, value ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {

                OutlinedTextField(
                    value = value,
                    onValueChange = { onPassengerChange(index, it) },
                    label = { Text("${index + 2}) Nombre del Pasajero / Pasaporte o ID") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(Modifier.width(8.dp))

                // 🔥 CORREGIDO
                Button(onClick = { onRemovePassenger(index) }) {
                    Text("❌")
                }
            }

            Spacer(Modifier.height(6.dp))
        }

        Spacer(Modifier.height(8.dp))

        Button(onClick = onAddPassenger) {
            Text("+(Opcional) Añadir Relación de Pasajeros")
        }
    }
}
@Composable
fun PaymentSection(
    tipoServicio: String,
    precioPorPersona: Int,
    precioTotal: Int,
    tipoPago: String,
    estadoPago: String,
    comprobantePago: String,
    onTipoPagoChange: (String) -> Unit,
    onPrecioPorPersonaChange: (Int) -> Unit,
    onEstadoPagoChange: (String) -> Unit,
    onComprobantePagoChange: (String) -> Unit,
) {
    Section("Pago") {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            ComboBox(
                label = "Tipo de Pago",
                options = listOf("POS",  "Efectivo","Pago Link", "Transferencia"),
                selected = tipoPago,
                onSelect = onTipoPagoChange,
                modifier = Modifier.weight(1f)
            )

            OutlinedTextField(
                value = precioPorPersona.toString(),
                onValueChange = { text ->
                    val digitos = text.filter { it.isDigit() }
                    onPrecioPorPersonaChange(digitos.toIntOrNull() ?: 0)
                },
                label = { Text("Precio por Persona (USD)") },
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = "USD $precioTotal",
                onValueChange = {},
                label = { Text("Precio Total") },
                readOnly = true,
                modifier = Modifier.weight(1f)
            )
            ComboBox(
                label = "Estado de Pago",
                options = listOf("PAGADO" ,"PENDIENTE PAGO" ,"CREDITO AGENCIA" ,"SERVICIO GRATUITO"),
                selected = estadoPago,
                onSelect = onEstadoPagoChange,
                modifier = Modifier.weight(1f)
            )
        }
        ComboBox(
                    label = "Comprobante de Pago",
                    options = listOf("NO APLICA","FACTURA CON IGV","FACTURA EXONERADA","BOLETA CON IGV","BOLETA EXONERADA","RECIBO INTERNO"),
                    selected = comprobantePago,
                    onSelect = onComprobantePagoChange,
                    modifier = Modifier.fillMaxWidth()
                )

        }
}
@Composable
fun StaffSection(
    driver: String,
    guia: String,
    observacion: String,
    observacionGeneral: String,
    countryCodewaAgente: String,
    countryCodewaDriver: String,
    waDriver: String,
    countryCodewaGuia: String,
    waGuia: String,
    onDriverChange: (String) -> Unit,
    onGuiaChange: (String) -> Unit,
    onObservacionChange: (String) -> Unit,
    onWaAgenteCountry: (String) -> Unit,
    onWaDriverCountry: (String) -> Unit,
    onWaDriverChange: (String) -> Unit,
    onWaGuiaCountry: (String) -> Unit,
    onWaGuiaChange: (String) -> Unit,
    onObservacionGeneralChange: (String) -> Unit
) {

    Section("Observaciones Internas") {
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = observacion,
            onValueChange = onObservacionChange,
            label = { Text("") },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Ejemplo: Comisiones de venta; Endoces; Logisticas internas; Instrucciones internas") }
        )
        Spacer(Modifier.height(8.dp))
    }

    Section("Observaciones en General") {
        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = observacionGeneral,
            onValueChange = onObservacionGeneralChange,
            label = { Text("") },
            readOnly = false,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Ejemplo: Observaciones en general; Solicitudes especiales") }
        )
        Spacer(Modifier.height(8.dp))
    }

    Section("Personal Asignado") {

        Spacer(Modifier.height(8.dp))

        val driverText = "${driver} / ${waDriver}"

        OutlinedTextField(
            value = driverText,
            onValueChange = { text ->
                val parts = text.split("/", limit = 2)
                val nombre = parts.getOrNull(0)?.trim() ?: ""
                val telefono = parts.getOrNull(1)?.trim() ?: ""

                onDriverChange(nombre)
                onWaDriverChange(telefono)
            },
            label = { Text("Driver Asignado (Nombre / Teléfono)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        val guiaText = "${guia} / ${waGuia}"

        OutlinedTextField(
            value = guiaText,
            onValueChange = { text ->
                val parts = text.split("/", limit = 2)
                val nombre = parts.getOrNull(0)?.trim() ?: ""
                val telefono = parts.getOrNull(1)?.trim() ?: ""

                onGuiaChange(nombre)
                onWaGuiaChange(telefono)
            },
            label = { Text("Guía Asignado (Nombre / Teléfono)") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}