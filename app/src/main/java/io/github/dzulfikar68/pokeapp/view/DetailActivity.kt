package io.github.dzulfikar68.pokeapp.view

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import io.github.dzulfikar68.pokeapp.model.CharPokemonResponse
import io.github.dzulfikar68.pokeapp.model.FormResponse
import io.github.dzulfikar68.pokeapp.model.PokemonService
import io.github.dzulfikar68.pokeapp.databinding.ActivityDetailBinding
import io.github.dzulfikar68.pokeapp.viewmodel.DetailViewModel
import io.github.dzulfikar68.pokeapp.viewmodel.ViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        binding.vpDesc.adapter = DescPagerAdapter(supportFragmentManager)
        binding.tlDesc.setupWithViewPager(binding.vpDesc)

        val id = intent?.getIntExtra("id", 0) ?: 0
        if (id != 0) {
            viewModel.getPokemonDetail(id)
            viewModel.getPokemonEvolutions(id)
        }

        val name = intent?.getStringExtra("name") ?: ""
        if (name != "") viewModel.getPokemonForm(name)

        viewModel.pokemonDetail?.observe(this, {
            if (it.isError) {
                Toast.makeText(
                    this@DetailActivity,
                    it.message,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val data = it.data
                val desc = data?.flavor_text_entries?.find { it.language?.name == "en" }?.flavor_text
                binding.tvDesc.text = desc
            }
        })
        viewModel.pokemonForm?.observe(this, {
            if (!it.isError) {
                val data = it.data
                val image = data?.sprites?.front_shiny
                Glide.with(binding.root.context)
                    .load(image)
                    .into(binding.ivPoke)
            }
        })
    }

        //======================================================

    private fun getDetail(id: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(PokemonService::class.java)
        service.getPokemonChar(id).enqueue(object : Callback<CharPokemonResponse> {
            override fun onResponse(
                call: Call<CharPokemonResponse>,
                response: Response<CharPokemonResponse>
            ) {
                val data = response.body()
                val listDesc = data?.descriptions ?: listOf()
                val itemDesc = listDesc.find { it.language?.name == "en" }
                binding.tvDesc.text = itemDesc?.description ?: "-"
//                progressDialog.dismiss()
            }

            override fun onFailure(call: Call<CharPokemonResponse>, t: Throwable) {
//                progressDialog.dismiss()
                Toast.makeText(
                    this@DetailActivity,
                    "Terjadi Kesalahan",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun getImage(name: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(PokemonService::class.java)
        service.getForm(name).enqueue(object : Callback<FormResponse> {
            override fun onResponse(
                    call: Call<FormResponse>,
                    response: Response<FormResponse>
            ) {
                val image = response.body()?.sprites?.front_shiny
                Glide.with(binding.root.context)
                    .load(image)
                    .into(binding.ivPoke)
            }

            override fun onFailure(call: Call<FormResponse>, t: Throwable) {
            }
        })
    }
}