package com.tuempresa.miapp.data
data class LoginResponse(val success: Boolean, val message: String, val userId: Int? = null)
data class GenericResponse(
    val success: Boolean,
    val id: Int? = null,      // 🔥 Agrega esto para capturar el mysql_id del PHP
    val message: String? = null
)
data class UpdateEstadoPagoRequest(
    val id: Int,
    val estadoPago: String
)