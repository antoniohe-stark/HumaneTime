package com.example.humanetime.api
import com.example.humanetime.api.model.LoginRequest
import com.example.humanetime.api.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("api/Authorization/AccesoUsuario")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
}
