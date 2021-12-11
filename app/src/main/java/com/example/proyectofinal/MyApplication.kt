package com.example.proyectofinal

import android.app.Application
import com.example.proyectofinal.database.DatabaseManager

open class MyApplication: Application() {
    override fun onCreate() {
        DatabaseManager.instance.initializeDb(applicationContext)
        super.onCreate()
    }
}