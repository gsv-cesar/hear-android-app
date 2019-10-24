package com.mpes.hear

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val btn = findViewById<Button>(R.id.login_btnentrar)
        btn.setOnClickListener {
            val intent = Intent(this, ContatoEmergenciaActivity::class.java)
            startActivity(intent)
            finish()
        }

        val txt = findViewById<TextView>(R.id.login_txtcad)
        txt.setOnClickListener {
            val intent = Intent(this, CadastroUsuarioActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return true
    }
}
