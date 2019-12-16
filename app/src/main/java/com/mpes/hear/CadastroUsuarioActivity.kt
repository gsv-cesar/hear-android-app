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
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception
import kotlin.collections.HashMap

class CadastroUsuarioActivity : AppCompatActivity() {

    lateinit var db: CollectionReference

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_usuario)

        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        db = FirebaseFirestore.getInstance().collection("cadastro")
        auth = FirebaseAuth.getInstance()

        val button: Button = findViewById(R.id.cadastro_btn)

        button.setOnClickListener {  auth()
        }

        val txt = findViewById<TextView>(R.id.cadastro_txtlogin)
        txt.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun prepDados()
    : HashMap<String, String>? {

        val nomeTxt = findViewById<View>(R.id.cadastro_nome_txt) as EditText
        val telefoneTxt = findViewById<View>(R.id.cadastro_telefone_txt) as EditText
        val emailTxt = findViewById<View>(R.id.cadastro_email_txt) as EditText
        val senhaTxt = findViewById<View>(R.id.cadastro_senha_txt) as EditText

        val nome = nomeTxt.text.toString().trim()
        val telefone = telefoneTxt.text.toString().trim()
        val email = emailTxt.text.toString().trim()
        val senha = senhaTxt.text.toString().trim()

        if (nome.isNotEmpty() && telefone.isNotEmpty() && email.isNotEmpty() && senha.isNotEmpty()) {

            val items = HashMap<String, String>()
            items.put("nome", nome)
            items.put("telefone", telefone)
            items.put("email", email)
            items.put("senha", senha)

            return items

        }

        return null

    }

    private fun auth(){

        try {
            if (prepDados()?.get("email") != null && prepDados()?.get("senha") != null) {
                auth.createUserWithEmailAndPassword(prepDados()?.get("email")!!, prepDados()?.get("senha")!!)
                    .addOnSuccessListener {
                        Toast.makeText(this,  "Autenticação OK", Toast.LENGTH_LONG).show()
                        cadastrar()
                    }
                    .addOnFailureListener{ ex ->

                        if (ex is FirebaseAuthUserCollisionException)
                            Toast.makeText(this,  "E-mail já existente!", Toast.LENGTH_LONG).show()
                        else
                            Toast.makeText(this,  ex.toString(), Toast.LENGTH_LONG).show()
                    }
            }


        }catch (e: Exception){
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
        }

    }

    private fun cadastrar(){

            try {

                if (prepDados() != null) {

                    val doc =   auth.uid.toString()

                        db.document(doc)
                        .set(prepDados()!!)
                        .addOnSuccessListener {
                            val intent = Intent(this, StartActivity::class.java)
                            startActivity(intent)
                            finish()
                            Toast.makeText(
                                this,
                                "Cadastro OK: ",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(
                                this,
                                "Cadastro ERRO",
                                Toast.LENGTH_LONG
                            ).show()
                        }
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