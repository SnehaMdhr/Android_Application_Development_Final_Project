package com.example.dailyexpensetracker.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dailyexpensetracker.R
import com.example.dailyexpensetracker.databinding.ActivityUpdateTransactionBinding
import com.example.dailyexpensetracker.repository.TransactionRepositoryImpl
import com.example.dailyexpensetracker.viewmodel.TransactionViewModel

class UpdateTransactionActivity : AppCompatActivity() {
    lateinit var binding: ActivityUpdateTransactionBinding

    lateinit var transactionViewModel: TransactionViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val repo=TransactionRepositoryImpl()
        transactionViewModel = TransactionViewModel(repo)

        var transactionId : String? = intent.getStringExtra("transactionIs")
        transactionViewModel.getTransactionbyId(transactionId.toString())
        transactionViewModel.transactions.observe(this){
            binding.updateAmount.setText(it?.amount.toString())
            binding.updateTransaction.setText(it?.transactionName.toString())
        }

        binding.btnUpdateTransaction.setOnClickListener{
            val transactionName = binding.updateTransaction.text.toString()
            val amount = binding.updateAmount.text.toString().toInt()

            var updatedMap = mutableMapOf<String,Any>()
            updatedMap["transactionName"] = transactionName
            updatedMap["amount"] = amount

            transactionViewModel.updateTransation(
                transactionId.toString(),
                updatedMap
            ){success,message ->
                if(success){
                    Toast.makeText(this@UpdateTransactionActivity, message, Toast.LENGTH_LONG).show()
                    finish()
                }else{
                    Toast.makeText(this@UpdateTransactionActivity,message, Toast.LENGTH_LONG).show()
                }
            }

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}