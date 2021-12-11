package com.example.proyectofinal.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.proyectofinal.database.Pokemon
import com.example.proyectofinal.databinding.FragmentRightBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import org.json.JSONObject

private lateinit var database: DatabaseReference
class RightFragment : Fragment() {
    private lateinit var binding: FragmentRightBinding
    private val rightViewModel: RightViewModel by viewModels()
    private lateinit var queue: RequestQueue
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myDB = FirebaseDatabase.getInstance()
        database = myDB.reference
        // Inflate the layout for this fragment
        binding= FragmentRightBinding.inflate(inflater,container,false)
        queue = Volley.newRequestQueue(context)
        binding.btnAgregar.isVisible=false
        binding.ivSprite.isVisible=false
        binding.btnBuscar.setOnClickListener { response ->
            if(binding.etBuscarPoke.text.toString() != ""){
                getPkmn()
            }
        }
        binding.btnAgregar.setOnClickListener {
            if(binding.tvID.text.toString() != ""){
                val user="01"
                database.child("usuarios").child(user).get().addOnSuccessListener { record->
                    val json = JSONObject(record.value.toString())
                    val numPkmn = json.getInt("capturados")
                    Log.d("updtNum", "Pokemon totales: $numPkmn")
                    val updtNum = hashMapOf<String, Any>(
                        "usuarios/${user}/capturados" to numPkmn +1
                    )
                    Log.d("updtNum", "Pokemon actuali: $updtNum")
                    database.updateChildren(updtNum)
                }
                val destination = RightFragmentDirections.actionRightFragmentToLeftFragment(binding.tvPokemonInfo.text.toString())
                NavHostFragment.findNavController(this).navigate(destination)
                rightViewModel.save(Pokemon(
                    binding.tvID.text.toString().toInt(),
                    binding.tvPokemonInfo.text.toString(),
                    binding.hp.text.toString(),
                    binding.atk.text.toString(),
                    binding.spatk.text.toString(),
                    binding.def.text.toString(),
                    binding.spdef.text.toString(),
                    binding.spd.text.toString(),
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${binding.tvID.text.toString()}.png"))
            }

        }
        return binding.root
    }
    fun getPkmn(){
        binding.btnAgregar.isVisible=true
        binding.detec.isVisible=false
        val url = "https://pokeapi.co/api/v2/pokemon/${binding.etBuscarPoke.text.toString().lowercase()}"
        val jsonRequest = JsonObjectRequest(url, Response.Listener<JSONObject>{response->
            binding.etBuscarPoke.text.clear()
            binding.tvPokemonInfo.setText(response.getString("name").replaceFirstChar { it.uppercaseChar() })
            binding.tvID.setText((response.getString("id")))
            binding.tipoL.setText("Tipo: ")
            binding.tipo.setText(response.getJSONArray("types").getJSONObject(0).getJSONObject("type").getString("name").replaceFirstChar { it.uppercaseChar() })
            binding.hpL.setText("HP: ")
            binding.hp.setText(response.getJSONArray("stats").getJSONObject(0).getString("base_stat"))
            binding.atkL.setText("Ataque: ")
            binding.atk.setText(response.getJSONArray("stats").getJSONObject(1).getString("base_stat"))
            binding.spatkL.setText("Ataque Especial: ")
            binding.spatk.setText(response.getJSONArray("stats").getJSONObject(3).getString("base_stat"))
            binding.defL.setText("Defensa: ")
            binding.def.setText(response.getJSONArray("stats").getJSONObject(2).getString("base_stat"))
            binding.spdefL.setText("Defensa Especial: ")
            binding.spdef.setText(response.getJSONArray("stats").getJSONObject(4).getString("base_stat"))
            binding.spdL.setText("Velocidad: ")
            binding.spd.setText(response.getJSONArray("stats").getJSONObject(5).getString("base_stat"))
            binding.weightL.setText("Peso: ")
            binding.weight.setText(response.getString("weight"))
            if(binding.tvID.text.length==1){
                val aidi="00${binding.tvID.text.toString()}"
                val img ="https://assets.pokemon.com/assets/cms2/img/pokedex/full/${aidi}.png"
                Picasso.get().load(img).into(binding.ivSprite)
            } else if (binding.tvID.text.length==2){
                val aidi="0${binding.tvID.text.toString()}"
                val img ="https://assets.pokemon.com/assets/cms2/img/pokedex/full/${aidi}.png"
                Picasso.get().load(img).into(binding.ivSprite)
            } else if(binding.tvID.text.length==3){
                val img ="https://assets.pokemon.com/assets/cms2/img/pokedex/full/${binding.tvID.text.toString()}.png"
                Picasso.get().load(img).into(binding.ivSprite)
            }
            binding.ivSprite.isVisible=true
            //val img = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${binding.tvID.text.toString()}.png"
        },
            Response.ErrorListener { errorMessage ->
                binding.etBuscarPoke.text.clear()
                binding.tvPokemonInfo.setText("404 Pokemon Not Found")
                binding.btnAgregar.isVisible=false
                binding.ivSprite.isVisible=false
                binding.detec.isVisible=true
                binding.tvID.setText("")
                binding.tipo.setText("")
                binding.hp.setText("")
                binding.atk.setText("")
                binding.spatk.setText("")
                binding.def.setText("")
                binding.spdef.setText("")
                binding.spd.setText("")
                binding.weight.setText("")
                binding.tipoL.setText("")
                binding.hpL.setText("")
                binding.atkL.setText("")
                binding.spatkL.setText("")
                binding.defL.setText("")
                binding.spdefL.setText("")
                binding.spdL.setText("")
                binding.weightL.setText("")
                Log.d("JSONResponse", "error: $errorMessage")
            }
        )
        queue.add(jsonRequest)
    }
    override fun onStop() {
        super.onStop()
        queue.cancelAll("stopped")
    }
}