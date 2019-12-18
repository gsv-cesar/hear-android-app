package com.mpes.hear

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.mpes.hear.firebase.Auth
import com.mpes.hear.firebase.Database
import com.mpes.hear.location.FusedLocation

class DashActivity : AppCompatActivity() {

    var db      = Database(this)
    var auth    = Auth(this)

    var telSMS: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dash)

        //Teste
        val fused = FusedLocation(this)
        fused.getLocation { latitude, longitude, endereco ->
            Log.d("Jholl", latitude.toString())
            Log.d("Jholl", longitude.toString())
            Log.d("Jholl", endereco?.thoroughfare)
        }
        //Teste

        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        db.getTelEmergencia(db.getCollection("cadastro"), auth.getUid()) {tel ->
            telSMS = tel
        }


        val btn_panico = findViewById<Button>(R.id.dash_btn_panico)
        btn_panico.setOnClickListener {

            val smsManager = SmsManager.getDefault() as SmsManager
            smsManager.sendTextMessage(telSMS, null, "Socorro! Estou precisando de ajuda.", null, null)
        }
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
