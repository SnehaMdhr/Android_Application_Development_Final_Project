package com.example.dailyexpensetracker.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.dailyexpensetracker.databinding.FragmentProfileBinding
import com.example.dailyexpensetracker.ui.activity.EditProfileActivity
import com.example.dailyexpensetracker.ui.activity.LoginActivity
import com.example.dailyexpensetracker.repository.UserRepositoryImpl
import com.example.dailyexpensetracker.viewmodel.UserViewModel

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(
            inflater,
            container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var repo = UserRepositoryImpl()
        userViewModel = UserViewModel(repo)

        var currentUser = userViewModel.getCurrentUser()

        currentUser.let {
            userViewModel.getUserFromDatabase(it?.uid.toString())
        }

        userViewModel.userData.observe(viewLifecycleOwner) { user ->
            user?.let {
                binding.profileName.text = it.username
                binding.profileEmail.text = it.email
            }
        }

        binding.editProfile.setOnClickListener {
            val intent = Intent(requireContext(), EditProfileActivity::class.java)
            startActivity(intent)
        }


        binding.logout.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { _, _ ->
                performLogout()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun performLogout() {
        userViewModel.logout { success, message ->
            if (success) {
                Toast.makeText(requireContext(), "Logged out successfully!", Toast.LENGTH_SHORT)
                    .show()

                // Redirect to LoginActivity and clear back stack
                val intent = Intent(requireContext(), LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "Logout failed: $message", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}