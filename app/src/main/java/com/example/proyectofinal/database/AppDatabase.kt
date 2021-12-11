package com.example.proyectofinal.database

import androidx.room.Database
import androidx.room.RoomDatabase

const val DATABASE_VERSION = 1
const val TABLE_POKEMONS = "pokemons"
const val DATABASE_NAME = "myappname.sqlite"

@Database(
    entities = [PokemonEntity::class],
    version = DATABASE_VERSION,
    exportSchema = false
)

abstract class AppDatabase: RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}