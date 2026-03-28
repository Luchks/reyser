package com.tuempresa.miapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComboBox(
    label: String,
    options: List<String>,
    selected: String,
    onSelect: (String) -> Unit,
    editableWhen: (String) -> Boolean = { false }, 
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    // ¿El valor actual se puede editar a mano?
    val isCustom = editableWhen(selected) || selected !in options
    val readOnly = !isCustom

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        // ⬇⬇ Campo de texto + overlay clickeable cuando es solo lectura
        Box(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        ) {
            OutlinedTextField(
                value = selected,
                onValueChange = { newText ->
                    if (!readOnly) {      // solo escribe cuando es editable (OTRO, etc.)
                        onSelect(newText)
                    }
                },
                readOnly = readOnly,
                label = { Text(label) },
                trailingIcon = {
                    // iconito estándar que rota
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    // Si prefieres tu ícono:
                    // Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // 🔥 Si NO es editable, toda la caja abre el menú
            if (readOnly) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expanded = true }
                )
            }
        }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        expanded = false
                        onSelect(option)   // si eliges "OTRO (editable)", el campo pasa a modo editable
                    }
                )
            }
        }
    }
}
