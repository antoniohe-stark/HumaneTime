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
import com.example.humanetime.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
//arreglar despues
//        if (!isLoggedIn) {
//            val intent = Intent(this, actLogin ::class.java)
//            startActivity(intent)
//            finish()   } else {
//              setContentView(R.layout.activity_main)
//        }

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

        navView.setupWithNavController(navController)

        navView.setNavigationItemSelectedListener { op ->
            when (op.itemId) {
                R.id.nav_home -> {
                    navController.navigate(R.id.nav_home)
                }

                R.id.nav_salir -> {
                    Toast.makeText(this, "Salir seleccionado", Toast.LENGTH_SHORT).show()
                    showAlert("Atención", "Esta acción cerrará la sesión actual")

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

    private fun showAlert(title: String, message: String) {
        if (!isFinishing && !isDestroyed) {
            AlertDialog.Builder(this).setTitle(title).setMessage(message)
                .setPositiveButton("Aceptar") { dialog, _ -> dialog.dismiss() }.create().show()
        }
    }
}