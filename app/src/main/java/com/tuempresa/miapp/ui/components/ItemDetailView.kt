package com.tuempresa.miapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tuempresa.miapp.data.Item

@Composable
fun ItemDetail(
    item: Item,
    onUpdate: (Item) -> Unit
) {
    var editMode by remember { mutableStateOf(false) }
    var editableItem by remember { mutableStateOf(item) }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = if (editMode) "Editar Reserva" else "Detalle Reserva",
                style = MaterialTheme.typography.titleLarge
            )
            TextButton(onClick = { editMode = !editMode }) {
                Text(if (editMode) "Cancelar" else "Editar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (!editMode) {
            // 🔹 MODO LECTURA
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    LabelValueRow(label = "Código Reserva", value = item.codigoReserva)
                    LabelValueRow(label = "Tour", value = item.nombreTour)
                    LabelValueRow(label = "Cliente", value = item.nombrePrincipal)
                    LabelValueRow(label = "Fecha", value = item.fecha)
                    LabelValueRow(label = "Hora", value = item.horaInicio)
                    LabelValueRow(label = "Estado Pago", value = item.estadoPago)
                    LabelValueRow(label = "Observación", value = item.observacionGeneral)
                }
            }
        } else {
            // 🔹 MODO EDICIÓN
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                item {
                    OutlinedTextField(
                        value = editableItem.nombrePrincipal,
                        onValueChange = { editableItem = editableItem.copy(nombrePrincipal = it) },
                        label = { Text("Cliente") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = editableItem.codigoReserva,
                        onValueChange = { editableItem = editableItem.copy(codigoReserva = it) },
                        label = { Text("Código Reserva") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    // 🔹 agrega los demás campos aquí
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            onUpdate(editableItem)
                            editMode = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Guardar")
                    }
                }
            }
        }
    }
}

@Composable
fun LabelValueRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.width(150.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
