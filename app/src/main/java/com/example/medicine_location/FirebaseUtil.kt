package com.example.medicine_location

import com.google.firebase.database.FirebaseDatabase

object FirebaseUtil {

    private var firebaseDatabase: FirebaseDatabase? = null

    fun getFirebaseDatabaseInstance(): FirebaseDatabase {
        if (firebaseDatabase == null) {
            firebaseDatabase = FirebaseDatabase.getInstance()
            firebaseDatabase?.setPersistenceEnabled(true)
        }
        return firebaseDatabase!!
    }

}