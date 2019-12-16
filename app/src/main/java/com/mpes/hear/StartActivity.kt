package com.mpes.hear

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.mpes.hear.firebase.Auth
import com.mpes.hear.firebase.Database

class StartActivity : AppCompatActivity() {

    var auth    = Auth(this)
    var db      = Database(this)

    lateinit var txt: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        supportActionBar?.hide()

        val button = findViewById<Button>(R.id.start_button)
        button.setOnClickListener {

            if (auth.getUser() != null){

                db.getTelEmergencia(db.getCollection("cadastro"), auth.getUid()) {tel ->
                    if (tel != null && tel != "") {
                        val intent = Intent(this, DashActivity::class.java)
                        startActivity(intent)

                    }else{
                        val intent = Intent(this, ContatoEmergenciaActivity::class.java)
                        startActivity(intent)

                    }
                }
            }else{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        txt = findViewById<TextView>(R.id.start_txtcad)
        txt.setOnClickListener {
            val intent = Intent(this, CadastroUsuarioActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        txt.isGone = auth.getUser() != null
    }
}
