package com.example.dailyexpensetracker.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dailyexpensetracker.R
import com.example.dailyexpensetracker.databinding.ActivityEditProfileBinding
import com.example.dailyexpensetracker.repository.UserRepositoryImpl
import com.example.dailyexpensetracker.viewmodel.UserViewModel

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ViewModel
        val repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)

        val currentUser = userViewModel.getCurrentUser()
        if (currentUser != null) {
            userViewModel.getUserFromDatabase(currentUser.uid)
        }

        // Observe user data
        userViewModel.userData.observe(this) { user ->
            if (user != null) {
                binding.editName.setText(user.username)
                binding.editAddress.setText(user.address)
                binding.editEmail.setText(user.email)
            }
        }

        // Save button click listener
        binding.btnSaveProfile.setOnClickListener {
            updateUserProfile()
        }
    }

    private fun updateUserProfile() {
        val username = binding.editName.text.toString().trim()
        val address = binding.editAddress.text.toString().trim()
        val email = binding.editEmail.text.toString().trim()

        if (username.isEmpty() || address.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val currentUser = userViewModel.getCurrentUser()
        if (currentUser != null) {
            val userId = currentUser.uid
            val updateData = mutableMapOf<String, Any>(
                "username" to username,
                "address" to address,
                "email" to email
            )

            userViewModel.editProfile(userId, updateData) { success, message ->
                if (success) {
                    Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show()
                    finish()  // Close the activity after update
                } else {
                    Toast.makeText(this, "Update failed: $message", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}