package com.example.dailyexpensetracker.repository

import com.google.firebase.auth.FirebaseAuth

class AuthRepoImpl(val auth: FirebaseAuth) : AuthRepo {
    override fun login(email: String, password: String, callback: (Boolean, String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, "Login successfull")
            } else {
                callback(false, it.exception?.message.toString())

            }
        }
    }
}