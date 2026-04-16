package com.tuempresa.miapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.tuempresa.miapp.model.Country
import com.tuempresa.miapp.model.countries
import androidx.compose.foundation.text.KeyboardOptions
import com.tuempresa.miapp.components.CountryPickerDialog

@Composable
fun PhoneVerificationScreen(modifier: Modifier = Modifier) {
    var showCountryPicker by remember { mutableStateOf(false) }
    var selectedCountry by remember {
        mutableStateOf<Country?>(countries.firstOrNull { it.name == "Perú" })
    }
    var phone by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Column(
            modifier = Modifier.align(Alignment.TopCenter),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "Ingresa tu número de teléfono",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )


            // Selector de país (similar a "España ▼")
            OutlinedButton(
                onClick = { showCountryPicker = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = selectedCountry?.name ?: "Elige un país",
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Elegir país"
                )
            }

            // Campo de teléfono con prefijo
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Número de tel.") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true,
                prefix = {
                    Text(
                        text = selectedCountry?.dialCode ?: "+",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                }
            )

        }

        // Botón SIG. en la parte de abajo
        Button(
            onClick = { /* TODO: acción de siguiente */ },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            Text("SIG.")
        }
    }

    // Diálogo de países
    CountryPickerDialog(
        isVisible = showCountryPicker,
        onDismiss = { showCountryPicker = false },
        onCountrySelected = { selectedCountry = it }
    )
}
