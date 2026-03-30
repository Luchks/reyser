package com.tuempresa.miapp.data

import okhttp3.Cache
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    //private const val BASE_URL = "http://10.106.55.25:8000/"
    private const val BASE_URL = "https://limaviptravel.pe/reservacion/"

    private val cookieJar = object : CookieJar {
        private val store = mutableMapOf<String, List<Cookie>>()
        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) { store[url.host] = cookies }
        override fun loadForRequest(url: HttpUrl): List<Cookie> = store[url.host] ?: emptyList()
    }

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        // Timeouts: sin estos, sin internet la app se queda colgada hasta que el sistema la mata
        .connectTimeout(8, TimeUnit.SECONDS)   // tiempo máximo para establecer conexión
        .readTimeout(15, TimeUnit.SECONDS)     // tiempo máximo esperando respuesta del servidor
        .writeTimeout(15, TimeUnit.SECONDS)    // tiempo máximo enviando datos
        .addInterceptor(logging)
        .cookieJar(cookieJar)
        // ✅ FIX CRÍTICO: fuerza que TODAS las peticiones vayan al servidor real,
        //    nunca a caché. Sin esto, OkHttp cachea la respuesta GET de
        //    check_updates.php y devuelve la firma antigua, haciendo que un
        //    dispositivo nunca detecte los cambios hechos desde otro.
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .header("Cache-Control", "no-cache, no-store")
                .header("Pragma", "no-cache")
                .build()
            chain.proceed(request)
        }
        // ✅ Deshabilitar caché en disco de OkHttp completamente
        .cache(null)
        .build()

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
//****************************************************************8
