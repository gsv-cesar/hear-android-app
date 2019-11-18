package com.mpes.hear

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val emailTxt     = findViewById<View>(R.id.login_emailTxt) as EditText
        val senhaTxt = findViewById<View>(R.id.login_senhaTxt) as EditText

        val email       = emailTxt.text.toString().trim()
        val senha       = senhaTxt.text.toString().trim()

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

    override fun onStart() {
        super.onStart()
        val usuarioAtivo = auth.currentUser
        atualizarUI(usuarioAtivo)
    }

    private fun atualizarUI(usuario: FirebaseUser?) {
        if (usuario != null){
            val intent = Intent(this, DashActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return true
    }
}
