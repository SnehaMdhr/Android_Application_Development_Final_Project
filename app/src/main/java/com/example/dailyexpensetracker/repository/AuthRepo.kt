package com.example.dailyexpensetracker.repository

interface AuthRepo {
    fun login(email:String,password:String,callback:(Boolean,String) -> Unit)
}