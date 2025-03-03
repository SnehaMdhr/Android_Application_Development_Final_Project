package com.example.dailyexpensetracker.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.dailyexpensetracker.R
import com.example.dailyexpensetracker.databinding.ActivityLoginBinding
import com.example.dailyexpensetracker.repository.UserRepositoryImpl
import com.example.dailyexpensetracker.utils.LoadingUtils
import com.example.dailyexpensetracker.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var userViewModel: UserViewModel
    lateinit var loadingUtils: LoadingUtils
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding =ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)

        loadingUtils = LoadingUtils(this)

        binding.loginBtn.setOnClickListener {
            loadingUtils.show()
            var email :String = binding.loginEmail.text.toString()
            var password :String = binding.loginPassword.text.toString()

            userViewModel.login(email,password){
                    success,message->
                if(success){
                    Toast.makeText(this@LoginActivity,message, Toast.LENGTH_LONG).show()
                    loadingUtils.dismiss()
                    var intent = Intent(this@LoginActivity,DashboardActivity::class.java)
                    startActivity(intent)
                    binding.displayLogin.text ="login success"
                    binding.displayLogin.visibility = View.GONE
                    finish()
                }else{
                    Toast.makeText(applicationContext,message, Toast.LENGTH_LONG).show()
                    loadingUtils.dismiss()
                }
            }
        }

        binding.register.setOnClickListener{
            val intent = Intent(this@LoginActivity,RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.forget.setOnClickListener{
            val intent = Intent(this@LoginActivity,ForgetPasswordActivity::class.java)
            startActivity(intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}