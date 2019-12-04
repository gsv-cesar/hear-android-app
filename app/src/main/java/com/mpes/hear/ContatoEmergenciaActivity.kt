package com.mpes.hear

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.util.HashMap
import kotlin.Exception

class ContatoEmergenciaActivity : AppCompatActivity() {

    lateinit var dbEmerg: DocumentReference
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contato_emergencia)

        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        auth = FirebaseAuth.getInstance()
        val doc =   auth.uid.toString()
        dbEmerg = FirebaseFirestore.getInstance().collection("cadastro").document(doc)

        val button: Button = findViewById(R.id.emergencia_btn)

        button.setOnClickListener {  cadastrarContato()
        }


    }

    private fun cadastrarContato(){

        val emergNomeTxt     = findViewById<View>(R.id.emergencia_nome_txt) as EditText
        val emergTelefoneTxt = findViewById<View>(R.id.emergencia_telefone_txt) as EditText

        val nome        = emergNomeTxt.text.toString().trim()
        val telefone    = emergTelefoneTxt.text.toString().trim()

        if (nome.isNotEmpty() && telefone.isNotEmpty()){

            val itemsEmerg = HashMap<String, Any>()
            itemsEmerg.put("nomeEmergencia", nome)
            itemsEmerg.put("telefoneEmergencia", telefone)

            try {

                dbEmerg
                    .set(itemsEmerg, SetOptions.merge())
                    .addOnSuccessListener {
                        Toast.makeText(this,  "Contato de Emergência OK", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, DashActivity::class.java)
                        startActivity(intent)
                        finish() }
                    .addOnFailureListener{ Toast.makeText(this,  "ERRO", Toast.LENGTH_LONG).show() }
            }catch (e: Exception){
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
            }

        }else{
            Toast.makeText(this,  "Por favor, preencha os campos", Toast.LENGTH_LONG).show()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        finish()
        return true
    }
}
