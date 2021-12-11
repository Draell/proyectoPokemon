package com.example.proyectofinal.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = TABLE_POKEMONS)
data class PokemonEntity(
    @PrimaryKey val pokemonID: Int,
    val pokemonName: String,
    val pokemonHp: String,
    val pokemonAtk: String,
    val pokemonAtkSpc: String,
    val pokemonDef: String,
    val pokemonDefSpc: String,
    val pokemonSpd: String,
    val pokemonSprite: String
)

fun PokemonEntity.toPokemon() = Pokemon(
    pokemonID, pokemonName, pokemonHp, pokemonAtk, pokemonAtkSpc, pokemonDef, pokemonDefSpc, pokemonSpd, pokemonSprite
)