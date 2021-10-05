package io.github.dzulfikar68.pokeapp.view

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import io.github.dzulfikar68.pokeapp.capitalizeWords
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

        supportActionBar?.title = "Detail Pokemon"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        binding.vpDesc.adapter = DescPagerAdapter(supportFragmentManager)
        binding.tlDesc.setupWithViewPager(binding.vpDesc)

        val id = intent?.getIntExtra("id", 0) ?: 0
        if (id != 0) {
            viewModel.getPokemonDetail(id)
            viewModel.getPokemonEvolutions(id)
            viewModel.getPokemonAbility(id)
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
                binding.tvDesc.text = desc?.capitalizeWords()
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}