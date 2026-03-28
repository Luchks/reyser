package com.tuempresa.miapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.tuempresa.miapp.components.CountryPickerDialog
import com.tuempresa.miapp.model.countries
@Composable
fun WhatsAppInput(
    modifier: Modifier = Modifier,
    label: String = "Numero de WhatsApp",
    currentCountryCode: String,
    currentPhoneNumber: String,
    onCountryCodeChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit
) {
    var showCountryPicker by remember { mutableStateOf(false) }

    var countryCode by remember { mutableStateOf(currentCountryCode) }

    var selectedCountry by remember {
        mutableStateOf(
            countries.find { it.dialCode == currentCountryCode }
                ?: countries.firstOrNull { it.name == "Perú" }
                ?: countries.first()
        )
    }

    LaunchedEffect(currentCountryCode) {
        countryCode = currentCountryCode
        countries.find { it.dialCode == currentCountryCode }?.let {
            selectedCountry = it
        }
    }

    var phone by remember { mutableStateOf(currentPhoneNumber) }
    LaunchedEffect(currentPhoneNumber) {
        phone = currentPhoneNumber
    }

    
    Column(modifier = modifier) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            
            Column(
                modifier = Modifier.weight(0.4f)
            ) {
                // ✅ ETIQUETA
                Text(
                    text = "Código de País",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(Modifier.height(4.dp))

                // 🇵🇪 SELECTOR DE PAÍS
                OutlinedButton(
                    onClick = { showCountryPicker = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "${selectedCountry.emoji} ${selectedCountry.name}",
                        modifier = Modifier.weight(1f),
                        maxLines = 1
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                }
            }

            // 📱 WHATSAPP
            OutlinedTextField(
                value = phone,
                onValueChange = {
                    phone = it
                    onPhoneNumberChange(it)
                },
                label = { Text(label) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true,
                modifier = Modifier.weight(0.6f),
                prefix = {
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Text("+", fontWeight = FontWeight.SemiBold)

                        Spacer(Modifier.width(2.dp))

                        val digitsPart = countryCode.removePrefix("+")

                        BasicTextField(
                            value = digitsPart,
                            onValueChange = { new ->
                                val onlyDigits = new.filter { it.isDigit() }
                                val finalValue = "+$onlyDigits"
                                countryCode = finalValue
                                onCountryCodeChange(finalValue)

                                countries.find { it.dialCode == finalValue }?.let {
                                    selectedCountry = it
                                }
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            modifier = Modifier.width(50.dp)
                        )

                        Spacer(Modifier.width(4.dp))
                    }
                }
            )
        }
    }

    if (showCountryPicker) {
        CountryPickerDialog(
            isVisible = showCountryPicker,
            onDismiss = { showCountryPicker = false },
            onCountrySelected = { country ->
                selectedCountry = country
                countryCode = country.dialCode
                onCountryCodeChange(country.dialCode)
                showCountryPicker = false
            }
        )
    }
}
