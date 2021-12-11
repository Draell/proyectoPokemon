package com.example.proyectofinal.database

import android.content.Context
import androidx.room.Room

class DatabaseManager {
    lateinit var database: AppDatabase

    companion object{
        val instance = DatabaseManager()
    }

    fun initializeDb(context: Context){
        createDb(context)
    }

    private fun createDb(context: Context){
        database = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}