package com.tuempresa.miapp.data
import retrofit2.Response
import retrofit2.http.*

data class UpdateCheckResponse(val success: Boolean, val signature: String)

interface ApiService {
    @FormUrlEncoded
    @POST("login.php")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<LoginResponse>
    
    @GET("get_items.php")
    suspend fun getItems(): List<Item>
    
    @GET("get_trash.php")
    suspend fun getTrash(): List<Item>
    
    @POST("create_item.php")
    suspend fun createItem(@Body item: Item): Response<GenericResponse>
    
    @POST("update_item.php")
    suspend fun updateItem(@Body item: Item): Response<GenericResponse>
    
    @FormUrlEncoded
    @POST("delete_item.php")
    suspend fun softDelete(@Field("id") id: Int): Response<GenericResponse>
    
    @FormUrlEncoded
    @POST("restore_item.php")
    suspend fun restore(@Field("id") id: Int): Response<GenericResponse>
    
    @FormUrlEncoded
    @POST("hard_delete_item.php")
    suspend fun hardDelete(@Field("id") id: Int): Response<GenericResponse>
    
    // ✅ CORREGIDO - Usa la data class específica
    @POST("update_estado_pago.php")
    suspend fun updateEstadoPago(@Body request: UpdateEstadoPagoRequest): Response<GenericResponse>

    @GET("check_updates.php")
    suspend fun checkUpdates(): UpdateCheckResponse

}
