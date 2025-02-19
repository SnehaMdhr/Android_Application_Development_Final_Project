package com.example.dailyexpensetracker.repository

import com.example.dailyexpensetracker.model.TransactionModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TransactionRepositoryImpl: TransactionRepository {
    val database: FirebaseDatabase =
        FirebaseDatabase.getInstance()

    val reference: DatabaseReference = database.reference.child("transactions")
    override fun addTransaction(
        transactionModel: TransactionModel,
        callback: (Boolean, String) -> Unit
    ) {
        var id = reference.push().key.toString()
        transactionModel.transactionId = id
        reference.child(id).setValue(transactionModel)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(true, "Product Added succesfully")
                } else {
                    callback(false, "${it.exception?.message}")
                }
            }
    }

    override fun updateTransation(
        transactionId: String,
        data: MutableMap<String, Any>,
        callback: (Boolean, String) -> Unit
    ) {
        reference.child(transactionId).updateChildren(data)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(true, "Product Updated succesfully")
                } else {
                    callback(false, "${it.exception?.message}")
                }
            }
    }

    override fun deleteTransaction(transactionId: String, callback: (Boolean, String) -> Unit) {
        reference.child(transactionId).removeValue()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    callback(true, "Product deleted succesfully")
                } else {
                    callback(false, "${it.exception?.message}")
                }
            }
    }

    override fun getTransactionbyId(
        transactionId: String,
        callback: (TransactionModel?, Boolean, String) -> Unit
    ) {
        reference.child(transactionId)
            .addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        var model = snapshot.getValue(TransactionModel::class.java)
                        callback(model,true,"Data fetched")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(null,false,error.message.toString())
                }

            })
    }

    override fun getAllTransaction(callback: (List<TransactionModel>?, Boolean, String) -> Unit) {
        reference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var products = mutableListOf<TransactionModel>()
                if(snapshot.exists()){
                    for(eachProduct in snapshot.children){
                        var model = eachProduct.getValue(TransactionModel::class.java)
                        if(model != null){
                            products.add(model)
                        }
                    }
                    callback(products,true,"fetched")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(null,false,error.message.toString())
            }
        })
    }

}