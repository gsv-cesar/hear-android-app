package com.mpes.hear.firebase

import android.app.Activity
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.mpes.hear.models.Cadastro

class Database (activity: Activity) {

    var db      = FirebaseFirestore.getInstance()

    val act     = activity

    fun getCollection(collection: String): CollectionReference{
        return db.collection(collection)
    }

    fun getTelEmergencia(collectionReference: CollectionReference, uid: String, listener: (String) -> Unit){
        collectionReference.document(uid).get().addOnSuccessListener(act) { document ->
            val telEmerg = document["telefoneEmergencia"].toString()

            listener(telEmerg)

        }
    }

    fun getCadastro(collectionReference: CollectionReference, uid: String, listener: (Cadastro) -> Unit){
        collectionReference.document(uid).get().addOnSuccessListener(act) { document ->
            val cadastro = Cadastro()
            cadastro.nome               = document["nome"].toString()
            cadastro.email              = document["email"].toString()
            cadastro.nomeEmergencia     = document["nomeEmergencia"].toString()
            cadastro.telefone           = document["telefone"].toString()
            cadastro.telefoneEmergencia = document["telefoneEmergencia"].toString()
            cadastro.senha              = document["senha"].toString()


            listener(cadastro)

        }
    }



}