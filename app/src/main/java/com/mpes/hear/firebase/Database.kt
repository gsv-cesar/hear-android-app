package com.mpes.hear.firebase

import android.app.Activity
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

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

}