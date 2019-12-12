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

    fun getTelEmergencia(collectionReference: CollectionReference, uid: String, listener: DatabaseFireBaseListener){

       collectionReference.document(uid).get().addOnSuccessListener(act) { document ->
           val telEmerg = document["telefoneEmergencia"].toString()

           listener.OnDbCompleteListener(telEmerg)

        }
    }

}

interface  DatabaseFireBaseListener {
    fun OnDbCompleteListener(telEmerg: String)
}