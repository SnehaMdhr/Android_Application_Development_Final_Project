package com.example.dailyexpensetracker.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.dailyexpensetracker.R
import com.example.dailyexpensetracker.databinding.ActivityDashboard2Binding
import com.example.dailyexpensetracker.ui.fragment.HomeFragment
import com.example.dailyexpensetracker.ui.fragment.ProfileFragment

class DashboardActivity : AppCompatActivity() {
    lateinit var navigationbinding: ActivityDashboard2Binding

    private fun replaceFragment(fragment: Fragment){
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        navigationbinding = ActivityDashboard2Binding.inflate(layoutInflater)

        setContentView(navigationbinding.root)

        replaceFragment(HomeFragment())

        navigationbinding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menuHome -> replaceFragment(HomeFragment())
                R.id.menuProfile -> replaceFragment(ProfileFragment())
                else -> {}
            }
            true
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}