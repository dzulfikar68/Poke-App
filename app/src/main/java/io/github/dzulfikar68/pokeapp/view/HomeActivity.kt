package io.github.dzulfikar68.pokeapp.view

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.dzulfikar68.pokeapp.viewmodel.HomeViewModel
import io.github.dzulfikar68.pokeapp.databinding.ActivityHomeBinding
import io.github.dzulfikar68.pokeapp.viewmodel.ViewModelFactory

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var pokemonAdapter: PokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        val viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        pokemonAdapter = PokemonAdapter()
        with(binding.rvList) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = pokemonAdapter
        }

        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Now Loading")
        progressDialog.setCancelable(false)
        progressDialog.show()

        viewModel.getPokemonList().observe(this, {
            if (it.isError) {
                Toast.makeText(
                    this@HomeActivity,
                    it.message,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val data = it.data ?: listOf()
                pokemonAdapter.setList(data)
                progressDialog.dismiss()
            }
            progressDialog.hide()
        })
    }

}