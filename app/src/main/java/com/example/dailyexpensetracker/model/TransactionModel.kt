package com.example.dailyexpensetracker.model

data class TransactionModel(
    var transactionId: String = "",
    var transactionName: String = "",
    var amount: Int = 0
)