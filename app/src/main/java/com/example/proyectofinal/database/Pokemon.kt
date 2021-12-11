package com.example.proyectofinal.database


class Pokemon(PkID: Int, PkName: String, PkHp: String, PkAtk: String, PkSpAtk: String, PkDef: String, PkSpDef: String, PkSpd: String, PkSprite: String){

    val PkID = PkID

    val PkName = PkName

    val PkHp = PkHp

    val PkAtk = PkAtk

    val PkSpAtk = PkSpAtk

    val PkDef = PkDef

    val PkSpDef = PkSpDef

    val PkSpd = PkSpd

    val PkSprite = PkSprite
}

fun Pokemon.toPokemonEntity() = PokemonEntity(
    PkID, PkName, PkHp, PkAtk, PkSpAtk, PkDef, PkSpDef, PkSpd, PkSprite
)