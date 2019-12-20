package com.mpes.hear

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.telephony.SmsManager
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.mpes.hear.firebase.Auth
import com.mpes.hear.firebase.Database
import com.mpes.hear.location.FusedLocation
import com.mpes.hear.services.VoiceService


class DashActivity : AppCompatActivity() {

    lateinit var db: Database
    lateinit var auth: Auth
    lateinit var fused: FusedLocation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash)

        db      = Database(this)
        auth    = Auth(this)
        fused   = FusedLocation(this)

        Intent(this, VoiceService::class.java).also {
            startService(it)
        }

        Intent(this, VoiceService::class.java).also {
            val handler = Handler()
            handler.postDelayed(Runnable {
                // Actions to do after 10 seconds
                stopService(it)
            }, 10000)
        }

        if (!fused.isActive()) {
            AlertDialog.Builder(this)
                .setMessage("Para solicitar ajuda por favor ative seu gps.")
                .setTitle("Atenção")
                .setPositiveButton("Ok") { _, _ ->
                    val intent = Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("Cancelar") { _, _ ->
                    finish()
                }
                .create()
                .show()
        }

        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val btnPanico = findViewById<Button>(R.id.dash_btn_panico)
        btnPanico.setOnClickListener {

            msgPanico()
        }
    }

    fun msgPanico() {
        db.getCadastro(db.getCollection("cadastro"), auth.getUid()) {
            Log.d("Jhool",it.nome)

            fused.getLocation { latitude, longitude, endereco ->
                Log.d("Jholl", latitude.toString())
                Log.d("Jholl", longitude.toString())
                Log.d("Jholl", it.telefoneEmergencia)

                var msgText = "SOCORRO! Olá, sou ${it.nome}."
                if (endereco != null) {
                    msgText += " Estou precisando de ajuda. ${endereco.getAddressLine(0)}"
                }


                enviarSMS(it.telefoneEmergencia, msgText)
            }
        }
    }

    fun enviarSMS(telefone: String, mensagem: String) {
        val smsManager = SmsManager.getDefault() as SmsManager
        smsManager.sendMultipartTextMessage(telefone, null, smsManager.divideMessage(mensagem), null, null)
    }

    private fun signOut(){
        auth.sair()
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        return when (item?.itemId) {
            R.id.menu_sair_dash -> {
                signOut()
                true
            }
            else -> {
                finish()
                return true
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_sair, menu)
        return true
    }
}
