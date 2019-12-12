package com.mpes.hear

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.mpes.hear.firebase.Auth
import com.mpes.hear.firebase.Database
import com.mpes.hear.firebase.DatabaseFireBaseListener
import com.mpes.hear.firebase.LoginFireBaseListener

class LoginActivity : AppCompatActivity(), LoginFireBaseListener, DatabaseFireBaseListener {


    var db      = Database(this)
    var auth    = Auth(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val btn = findViewById<Button>(R.id.login_btnentrar)
        btn.setOnClickListener {

            val emailTxt     = findViewById<View>(R.id.login_emailTxt) as EditText
            val senhaTxt     = findViewById<View>(R.id.login_senhaTxt) as EditText

            val email       = emailTxt.text.toString().trim()
            val senha       = senhaTxt.text.toString().trim()

            if (email.isNotEmpty() && senha.isNotEmpty()) {
                signIn(email, senha)
            }else{
                Toast.makeText(this,  "Preencha o(s) campo(s)!", Toast.LENGTH_LONG).show()
            }

        }

        val txt = findViewById<TextView>(R.id.login_txtcad)
        txt.setOnClickListener {
            val intent = Intent(this, CadastroUsuarioActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun atualizarUI(usuario: FirebaseUser?, telEmerg: String?){

        if (usuario != null){

            Toast.makeText(this,  telEmerg, Toast.LENGTH_LONG).show()

            if (telEmerg != null && telEmerg != "") {
                val intent = Intent(this, DashActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                val intent = Intent(this, ContatoEmergenciaActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun signIn(email: String, senha: String){
        auth.entrar(email, senha, this)
    }

    override fun OnLoginCompleteListener(successful: Boolean) {

        Toast.makeText(this,  "Teste", Toast.LENGTH_LONG).show()

        if (successful) {
            db.getTelEmergencia(db.getCollection("cadastro"), auth.getUid(), this)
        }
        else {
            Toast.makeText(
                baseContext, "Falha na autenticação.",
                Toast.LENGTH_SHORT
            ).show()
            atualizarUI(null, null)
        }
    }

    override fun OnDbCompleteListener(telEmerg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        //atualizarUI(auth.getUser(), telEmerg)
        Toast.makeText(this,  telEmerg, Toast.LENGTH_LONG).show()

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return true
    }
}