package com.example.proyectofinal.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinal.database.DatabaseManager
import com.example.proyectofinal.database.MyAppDataSource
import com.example.proyectofinal.database.Pokemon
import kotlinx.coroutines.launch

class RightViewModel: ViewModel() {
    fun save(pokemon: Pokemon){
        viewModelScope.launch {
            val pokemonDao = DatabaseManager.instance.database.pokemonDao()
            MyAppDataSource(pokemonDao).save(pokemon)
        }
    }
}