package com.tuempresa.miapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentStatusDropdown(
    currentStatus: String,
    onStatusChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    
    var internalStatus by remember { mutableStateOf(currentStatus) }

    LaunchedEffect(currentStatus) {
        internalStatus = currentStatus
    }
    
    val options = listOf(
        "PAGADO",
        "PENDIENTE PAGO",
        "CREDITO AGENCIA",
        "SERVICIO GRATUITO"
    )
    
    val statusColor = when (internalStatus.uppercase().trim()) {
        "PAGADO" -> Color(0xFF4CAF50)
        "PENDIENTE PAGO" -> Color(0xFFE91E63)
        "CREDITO AGENCIA" -> Color(0xFFFF9800)
        "SERVICIO GRATUITO" -> Color(0xFF2196F3)
        else -> Color.Gray
    }

    Box(modifier = modifier) {
        Surface(
            onClick = { expanded = true },
            color = statusColor,
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier.heightIn(min = 28.dp)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = internalStatus.ifBlank { "SIN ESTADO" }.uppercase(),
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Cambiar estado",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                val optionColor = when (option) {
                    "PAGADO" -> Color(0xFF4CAF50)
                    "PENDIENTE PAGO" -> Color(0xFFE91E63)
                    "CREDITO AGENCIA" -> Color(0xFFFF9800)
                    "SERVICIO GRATUITO" -> Color(0xFF2196F3)
                    else -> Color.Gray
                }
                
                DropdownMenuItem(
                    text = {
                        Surface(
                            color = optionColor,
                            shape = RoundedCornerShape(4.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = option,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    },
                    onClick = {
                        expanded = false
                        if (option != internalStatus) {
                            internalStatus = option
                            onStatusChange(option)
                        }
                    }
                )
            }
        }
    }
}
