package io.github.dzulfikar68.pokeapp

import android.app.ProgressDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.dzulfikar68.pokeapp.databinding.ActivityHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var pokemonAdapter: PokemonAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pokemonAdapter = PokemonAdapter()
        with(binding.rvList) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = pokemonAdapter
        }

        getListItem()
    }

    private fun getListItem() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Sedang Menunggu (Loading)")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(PokemonService::class.java)
        service.getPokemon().enqueue(object : Callback<PokemonResponse> {
            override fun onResponse(
                call: Call<PokemonResponse>,
                response: Response<PokemonResponse>
            ) {
                val data = response.body()
                val listItem = data?.results ?: listOf()
                pokemonAdapter.setList(listItem)
                progressDialog.dismiss()
            }

            override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(
                    this@HomeActivity,
                    "Terjadi Kesalahan",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}