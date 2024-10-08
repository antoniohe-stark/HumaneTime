package com.example.humanetime

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isNotEmpty
import com.example.humanetime.api.ApiService
import com.example.humanetime.databinding.ActivityActLoginBinding

import com.example.humanetime.api.RetrofitClient
import com.example.humanetime.api.model.LoginRequest
import com.example.humanetime.api.model.LoginResponse
import com.example.humanetime.utils.ErrorMessages
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class actLogin : AppCompatActivity() {
    private var _binding: ActivityActLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityActLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()
    }

    fun initListeners() {
        binding.btnIngresar.setOnClickListener {

            val email = binding.inputMail.editText?.text.toString().trim()
            val password = binding.inputPassword.editText?.text.toString().trim()

            if (email.isNotEmpty()) {
                if (password.isNotEmpty()) {
                    login(email, password)
                    val intent = Intent(this@actLogin, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    showAlert("Aviso", "Ingrese su contraseña")
                    binding.inputPassword.requestFocus()
                }
            } else {
                showAlert("Aviso", "Ingrese su correo electrónico")
                binding.inputMail.requestFocus()
            }
        }


        binding.tvCrearCuenta.setOnClickListener {}

        binding.tvReestablecerPass.setOnClickListener {}

    }

    private fun login(username: String, password: String) {
        val retrofit = RetrofitClient.getClient()
        val apiService = retrofit.create(ApiService::class.java)
        val loginRequest = LoginRequest(username, password)

        val call = apiService.login(loginRequest)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("isLoggedIn", true)
                    editor.putString("token", loginResponse?.token)
                    editor.putString("nombreUsuario", loginResponse?.acceso?.usuarioTiempo?.nombreCompleto)
                    editor.putString("fotoUrl", loginResponse?.acceso?.usuarioTiempo?.foto)
                    editor.apply()

                    val intent = Intent(this@actLogin, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    showAlert("Aviso", " ${response.message()}");
                }

                if (response.body()?.error == true) {
                    val errorCode = response.body()?.codigo ?: "EG000"
                    val errorMessage = ErrorMessages.getErrorMessage(errorCode)
                    val errorService = ErrorMessages.getErrorService(errorCode)
                    showAlert("Error en $errorService", errorMessage)
                }

            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                showAlert("Aviso", "Error de conexión:  ${t.message}")
            }
        })
    }

    private fun showAlert(title: String, message: String) {
        if (!isFinishing && !isDestroyed) {
            AlertDialog.Builder(this).setTitle(title).setMessage(message)
                .setPositiveButton("Aceptar") { dialog, _ -> dialog.dismiss() }.create().show()
        }
    }
}