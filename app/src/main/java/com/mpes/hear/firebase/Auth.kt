package com.mpes.hear.firebase

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Auth (activity: Activity) {

    var auth = FirebaseAuth.getInstance()

    val act = activity

    fun entrar(email: String, senha: String, listener: LoginFireBaseListener){

        auth.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener(act) { task ->
                listener.OnLoginCompleteListener(task.isSuccessful)
            }
    }

    fun getUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun getUid(): String{
        return auth.uid.toString()
    }

}

interface  LoginFireBaseListener {
    fun OnLoginCompleteListener(successful: Boolean)
}