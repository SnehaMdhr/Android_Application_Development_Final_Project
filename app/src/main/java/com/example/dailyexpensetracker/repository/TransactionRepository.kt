package com.example.dailyexpensetracker.repository

import com.example.dailyexpensetracker.model.TransactionModel

interface TransactionRepository {
    fun addTransaction(transactionModel: TransactionModel,
                       callback:(Boolean,String) -> Unit
    )

    fun updateTransation(transactionId:String,
                         data: MutableMap<String,Any>,
                         callback: (Boolean, String) -> Unit)

    fun deleteTransaction(transactionId: String,
                          callback: (Boolean, String) -> Unit)

    fun getTransactionbyId(transactionId: String,
                           callback: (
                               TransactionModel?, Boolean,
                               String) -> Unit)

    fun getAllTransaction(callback:
                              (List<TransactionModel>?, Boolean,
                               String) -> Unit)

}