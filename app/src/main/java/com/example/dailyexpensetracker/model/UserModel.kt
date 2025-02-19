package com.example.dailyexpensetracker.model

import android.os.Parcelable

data class UserModel(
    val userId: String = "",
    val email: String = "",
    val username: String = "",
    val address: String = ""
)
