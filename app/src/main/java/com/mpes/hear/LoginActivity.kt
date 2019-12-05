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

class LoginActivity : AppCompatActivity() {

    lateinit var db: CollectionReference
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()
        //val doc =   auth.uid.toString()
        //db = FirebaseFirestore.getInstance().collection("cadastro/"+doc)

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

    override fun onStart() {
        super.onStart()
        val usuarioAtivo = auth.currentUser
        atualizarUI(usuarioAtivo)
    }

    private fun atualizarUI(usuario: FirebaseUser?){

        if (usuario != null){

            val intent = Intent(this, ContatoEmergenciaActivity::class.java)
                startActivity(intent)
                finish()
        }
    }

    private fun signIn(email: String, senha: String){

        auth.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    atualizarUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Falha na autenticação.                                                                                 ",
                        Toast.LENGTH_SHORT
                    ).show()
                    atualizarUI(null)
                }

            }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return true
    }
}