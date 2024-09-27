package com.example.humanetime.api.model
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("token") val token: String,
    @SerializedName("error") val error: Boolean,
    @SerializedName("codigo") val codigo: String,
    @SerializedName("acceso") val acceso: Acceso
)

data class Acceso(
    @SerializedName("codigo") val codigo: String,
    @SerializedName("ambiente") val ambiente: String,
    @SerializedName("expiration") val expiration: Boolean,
    @SerializedName("usuariotiempo") val usuarioTiempo: UsuarioTiempo
)

data class UsuarioTiempo(
    @SerializedName("idUsuario") val idUsuario: Int,
    @SerializedName("nombreCompleto") val nombreCompleto: String,
    @SerializedName("nombPat") val nombPat: String,
    @SerializedName("nombMat") val nombMat: String,
    @SerializedName("tipousuario") val tipoUsuario: Int,
    @SerializedName("foto") val foto: String
)
