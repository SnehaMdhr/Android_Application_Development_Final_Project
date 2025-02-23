package com.example.dailyexpensetracker.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dailyexpensetracker.R
import com.example.dailyexpensetracker.databinding.ActivityForgetPasswordBinding
import com.example.dailyexpensetracker.utils.LoadingUtils
import com.google.firebase.auth.FirebaseAuth

class ForgetPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var binding: ActivityForgetPasswordBinding
        lateinit var firebaseAuth: FirebaseAuth
        lateinit var loadingUtils: LoadingUtils
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)


        firebaseAuth = FirebaseAuth.getInstance()
        loadingUtils = LoadingUtils(this)
        binding.forgetbtn.setOnClickListener {
            loadingUtils.show()
            val email =  binding.forgetpwEmail.text.toString()
            if (email.isNotEmpty()){
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        loadingUtils.dismiss()
                        Toast.makeText(
                            this@ForgetPasswordActivity,
                            "Reset link sent to your email",
                            Toast.LENGTH_SHORT
                        ).show()
                    }else{
                        loadingUtils.dismiss()
                        Toast.makeText(
                            this@ForgetPasswordActivity,
                            "Error: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }else{
                Toast.makeText(
                    this@ForgetPasswordActivity,
                    "Please fill the field",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}