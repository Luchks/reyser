package com.tuempresa.miapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.tuempresa.miapp.model.Country
import com.tuempresa.miapp.model.countries

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryPickerDialog(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onCountrySelected: (Country) -> Unit
) {
    if (!isVisible) return

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(modifier = Modifier.fillMaxSize()) {

                // TopAppBar (compatible con Material3 antiguo)
                TopAppBar(
                    title = {
                        Text(
                            text = "Elige un país",
                            fontWeight = FontWeight.SemiBold
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onDismiss) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Volver"
                            )
                        }
                    }
                )

                var search by remember { mutableStateOf("") }

                OutlinedTextField(
                    value = search,
                    onValueChange = { search = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar"
                        )
                    },
                    label = { Text("Buscar país") },
                    singleLine = true
                )

                val filtered = remember(search) {
                    if (search.isBlank()) countries
                    else {
                        val q = search.trim().lowercase()
                        countries.filter {
                            it.name.lowercase().contains(q) ||
                                    it.dialCode.contains(q)
                        }
                    }
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filtered) { country ->
                        CountryRow(
                            country = country,
                            onClick = {
                                onCountrySelected(country)
                                onDismiss()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CountryRow(
    country: Country,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = country.emoji, style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = country.name, style = MaterialTheme.typography.bodyLarge)
        }

        Text(
            text = country.dialCode,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}
