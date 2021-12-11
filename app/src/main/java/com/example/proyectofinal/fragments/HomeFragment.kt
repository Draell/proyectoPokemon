package com.example.proyectofinal.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyectofinal.databinding.FragmentHomeBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.json.JSONObject

private lateinit var database: DatabaseReference
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentHomeBinding.inflate(inflater,container,false)
        val myDB = FirebaseDatabase.getInstance()
        database = myDB.reference

        database.child("usuarios").child("01").get().addOnSuccessListener { record ->
            val json = JSONObject(record.value.toString())
            Log.d("ValoresFirebase", "got value ${record.value}")
            binding.tvNick.setText(json.getString("nick").toString())
            binding.tvNombre.setText(json.getString("nombre").toString())
            binding.tvCreado2.setText(json.getString("creado").toString())
            binding.tvNivel2.setText(json.getString("nivel"))
            binding.tvCapturados2.setText(json.getString("capturados"))
        }
        return binding.root
    }

}