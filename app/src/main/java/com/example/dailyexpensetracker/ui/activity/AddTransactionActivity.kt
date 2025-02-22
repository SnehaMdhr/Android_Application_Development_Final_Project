package com.example.dailyexpensetracker.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dailyexpensetracker.R
import com.example.dailyexpensetracker.databinding.ActivityAddTransactionBinding
import com.example.dailyexpensetracker.model.TransactionModel
import com.example.dailyexpensetracker.repository.TransactionRepositoryImpl
import com.example.dailyexpensetracker.viewmodel.TransactionViewModel

class AddTransactionActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddTransactionBinding

    lateinit var transactionViewModel: TransactionViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAddTransactionBinding.inflate(layoutInflater) // ✅ Initialize binding first
        setContentView(binding.root)

        val repo = TransactionRepositoryImpl()
        transactionViewModel = TransactionViewModel(repo)

        binding.btnAddProduct.setOnClickListener {
            val transactionName = binding.editTransactionName.text.toString()
            val transactionAmount = binding.editAmount.text.toString().toIntOrNull() ?: 0

            val model = TransactionModel(
                "",
                transactionName,
                transactionAmount
            )

            transactionViewModel.addTransaction(model) { success, message ->
                Toast.makeText(this@AddTransactionActivity, message, Toast.LENGTH_LONG).show()
                if (success) {
                    finish()
                }
            }
        }
    }

}