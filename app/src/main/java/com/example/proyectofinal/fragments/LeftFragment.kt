package com.example.proyectofinal.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.RequestQueue
import com.example.proyectofinal.databinding.FragmentLeftBinding

class LeftFragment : Fragment() {
    private lateinit var queue: RequestQueue

    private lateinit var binding: FragmentLeftBinding
    private val leftViewModel: LeftViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLeftBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val receiverPokemon = arguments?.getString("namePokemon")
        binding.misPokes.layoutManager = LinearLayoutManager(view?.context)

        leftViewModel.getPokemons()
        leftViewModel.savedPokemon.observe(viewLifecycleOwner, { pokemonList ->
            if (!pokemonList.isNullOrEmpty()){
                val adapter = LeftAdapter(pokemonList, leftViewModel)
                binding.misPokes.adapter = adapter
                for(savedpkmn in pokemonList){
                    Log.d("obtenidos", "id reg: ${savedpkmn.PkID}, pokemon de fragment: ${savedpkmn.PkName}, sprite: ${savedpkmn.PkSprite}")
                }
            }else{
                Log.d("obtenidos", "Nada de nada")
            }
        })

    }
}