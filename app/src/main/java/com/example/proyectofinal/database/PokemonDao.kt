package com.example.proyectofinal.database

import androidx.room.*

@Dao
interface PokemonDao {
    @Delete
    fun delete(pokemon: PokemonEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun save(pokemon: PokemonEntity)

    @Query("select * from $TABLE_POKEMONS")
    fun getPokemonsFromDatabase(): List<PokemonEntity>
}