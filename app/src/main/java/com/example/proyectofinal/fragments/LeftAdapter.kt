package com.example.proyectofinal.fragments

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofinal.database.Pokemon
import com.example.proyectofinal.databinding.ItemPkmnBinding
import com.squareup.picasso.Picasso
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.json.JSONObject

private lateinit var database: DatabaseReference
class LeftAdapter(private val pokemons: List<Pokemon>, private val leftViewModel: LeftViewModel): RecyclerView.Adapter<LeftAdapter.LeftHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeftHolder {
        val binding = ItemPkmnBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LeftHolder(binding, leftViewModel)
    }

    override fun onBindViewHolder(holder: LeftHolder, position: Int) {
        holder.render(pokemons[position])
    }

    override fun getItemCount(): Int = pokemons.size
    class LeftHolder(val binding: ItemPkmnBinding, val leftViewModel: LeftViewModel): RecyclerView.ViewHolder(binding.root){

        fun render(pokemon: Pokemon){
            val myDB = FirebaseDatabase.getInstance()
            database = myDB.reference
            binding.pkNameL.setText(pokemon.PkName)
            binding.pkid.setText(pokemon.PkID.toString())
            binding.pkhp.setText(pokemon.PkHp)
            binding.pkatk.setText(pokemon.PkAtk)
            binding.pkspatk.setText(pokemon.PkSpAtk)
            binding.pkdef.setText(pokemon.PkDef)
            binding.pkspdef.setText(pokemon.PkSpDef)
            binding.pkspd.setText(pokemon.PkSpd)
            Picasso.get().load(pokemon.PkSprite).into(binding.pkSprite)

            val user="01"
            binding.btnBorrar.setOnClickListener {
                database.child("usuarios").child(user).get().addOnSuccessListener { record->

                    val json = JSONObject(record.value.toString())
                    val numPkmn = json.getInt("capturados")
                    Log.d("updtNum", "Pokemon totales: $numPkmn")
                    val updtNum = hashMapOf<String, Any>(
                        "usuarios/${user}/capturados" to numPkmn -1
                    )
                    Log.d("updtNum", "Pokemon actuali: $updtNum")
                    database.updateChildren(updtNum)
                }
                leftViewModel.delete(pokemon)
            }
        }
    }
}