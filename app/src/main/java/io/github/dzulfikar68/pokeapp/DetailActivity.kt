package io.github.dzulfikar68.pokeapp

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import io.github.dzulfikar68.pokeapp.databinding.ActivityDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.vpDesc.adapter = DescPagerAdapter(supportFragmentManager)
        binding.tlDesc.setupWithViewPager(binding.vpDesc)

        getDetailItem()
    }

    private fun getDetailItem() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Sedang Menunggu (Loading)")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(PokemonService::class.java)
        service.getPokemonChar(2).enqueue(object : Callback<CharPokemonResponse> {
            override fun onResponse(
                call: Call<CharPokemonResponse>,
                response: Response<CharPokemonResponse>
            ) {
                val data = response.body()
                val listDesc = data?.descriptions ?: listOf()
                val itemDesc = listDesc.find { it.language?.name == "en" }
                binding.tvDesc.text = itemDesc?.description ?: "-"
                progressDialog.dismiss()
            }

            override fun onFailure(call: Call<CharPokemonResponse>, t: Throwable) {
                progressDialog.dismiss()
                Toast.makeText(
                    this@DetailActivity,
                    "Terjadi Kesalahan",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}