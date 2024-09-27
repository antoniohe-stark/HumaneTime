package com.example.humanetime

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.humanetime.databinding.ActivityMainBinding
import com.example.humanetime.databinding.NavHeaderMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
    val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isLoggedIn) {
            val intent = Intent(this, actLogin ::class.java)
            startActivity(intent)
            finish()   } else {
              setContentView(R.layout.activity_main)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).setAnchorView(R.id.fab).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)


        val headerBinding = NavHeaderMainBinding.bind(binding.navView.getHeaderView(0))

        val nombreUsuarioTextView = headerBinding.tvUSerName
        val fotoUsuarioImageView = headerBinding.imgAvatar

        val nombreUsuario = sharedPreferences.getString("nombreUsuario", "Usuario")
        val fotoUrl = sharedPreferences.getString("fotoUrl", "")

        nombreUsuarioTextView.text = nombreUsuario

        if (!fotoUrl.isNullOrEmpty()) {
            Glide.with(this)
                .load(fotoUrl)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .into(fotoUsuarioImageView)
        }
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener { op ->
            when (op.itemId) {
                R.id.nav_home -> {
                    navController.navigate(R.id.nav_home)
                }
                R.id.nav_salir -> {
                    showAlert("Atención", "Esta acción cerrará la sesión actual") {
                        logout()
                    }
                }

                else -> {

                    Toast.makeText(
                        this,
                        "Contrate al desarrollador para desbloquear esta función",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            drawerLayout.closeDrawer(navView)
            true
        }
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


     fun logout() {
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
          editor.remove("isLoggedIn")
        editor.remove("token")
        editor.apply()
         val intent = Intent(this, actLogin::class.java)
        startActivity(intent)
        finish()
    }


    private fun showAlert(title: String, message: String, onPositive: () -> Unit) {
        if (!isFinishing && !isDestroyed) {
            AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Aceptar") { dialog, _ ->
                    dialog.dismiss()
                    onPositive()
                }
                .setNegativeButton("Cancelar") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        }
    }
}