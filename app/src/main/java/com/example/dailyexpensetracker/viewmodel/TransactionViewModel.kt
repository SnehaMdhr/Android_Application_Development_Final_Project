package com.example.dailyexpensetracker.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.dailyexpensetracker.model.TransactionModel
import com.example.dailyexpensetracker.repository.TransactionRepository

class TransactionViewModel(val repository: TransactionRepository) {
    fun addTransaction(transactionModel: TransactionModel,
                       callback:(Boolean,String) -> Unit
    ){
        repository.addTransaction(transactionModel,callback)
    }

    fun updateTransation(transactionId:String,
                         data: MutableMap<String,Any>,
                         callback: (Boolean, String) -> Unit){
        repository.updateTransation(transactionId, data, callback)
    }


    fun deleteTransaction(transactionId: String,
                          callback: (Boolean, String) -> Unit){
        repository.deleteTransaction(transactionId, callback)
    }

    var _transactions = MutableLiveData<TransactionModel?>()
    var transactions = MutableLiveData<TransactionModel?>()
        get() = _transactions

    var _alltransactions = MutableLiveData<List<TransactionModel>?>()
    var alltransactions = MutableLiveData<List<TransactionModel>?>()
        get() = _alltransactions

    fun getTransactionbyId(transactionId: String){
        repository.getTransactionbyId(transactionId){
                transaction,success,message->
            if(success){
                _transactions.value = transaction
            }
        }
    }

    fun getAllTransaction(){
        repository.getAllTransaction(){
                products, success, message ->
            if(success){
                _alltransactions.value = products
            }
        }
    }

}