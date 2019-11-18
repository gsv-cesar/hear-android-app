package com.mpes.hear

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.DocumentsContract
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.FirebaseAuthCredentialsProvider
import java.lang.Exception
import java.util.HashMap

class CadastroUsuarioActivity : AppCompatActivity() {

    lateinit var db: DocumentReference

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_usuario)

        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        db = FirebaseFirestore.getInstance().collection("cadastro").document()
        auth = FirebaseAuth.getInstance()

        val button: Button = findViewById(R.id.cadastro_btn)

        button.setOnClickListener {  cadastrar()
        }

        val txt = findViewById<TextView>(R.id.cadastro_txtlogin)
        txt.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun cadastrar(){

        val nomeTxt     = findViewById<View>(R.id.cadastro_nome_txt) as EditText
        val telefoneTxt = findViewById<View>(R.id.cadastro_telefone_txt) as EditText
        val emailTxt    = findViewById<View>(R.id.cadastro_email_txt) as EditText
        val senhaTxt    = findViewById<View>(R.id.cadastro_senha_txt) as EditText

        val nome        = nomeTxt.text.toString().trim()
        val telefone    = telefoneTxt.text.toString().trim()
        val email       = emailTxt.text.toString().trim()
        val senha       = senhaTxt.text.toString().trim()

        if (nome.isNotEmpty() && telefone.isNotEmpty() && email.isNotEmpty() && senha.isNotEmpty()){

            val items = HashMap<String, Any>()
            items.put("nome", nome)
            items.put("telefone", telefone)
            items.put("email", email)
            items.put("senha", senha)

            try {

                db
                    .set(items)
                    .addOnSuccessListener { Toast.makeText(this,  "Cadastro OK", Toast.LENGTH_LONG).show() }
                    .addOnFailureListener{Toast.makeText(this,  "Cadastro ERRO", Toast.LENGTH_LONG).show() }

                auth(email, senha)

            }catch (e: Exception){
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(this,  "Por favor, preencha os campos", Toast.LENGTH_LONG).show()
        }

    }

    private fun auth(email: String, senha: String){

        try {
            auth.createUserWithEmailAndPassword(email, senha)
                .addOnSuccessListener {
                    Toast.makeText(this,  "Autenticação OK", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener{ ex ->

                    if (ex is FirebaseAuthUserCollisionException)
                        Toast.makeText(this,  "E-mail já existente!", Toast.LENGTH_LONG).show()
                    else
                        Toast.makeText(this,  ex.toString(), Toast.LENGTH_LONG).show()
                }


        }catch (e: Exception){
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return true
    }
}