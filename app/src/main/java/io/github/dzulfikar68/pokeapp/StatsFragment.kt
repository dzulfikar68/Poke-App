package io.github.dzulfikar68.pokeapp

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import io.github.dzulfikar68.pokeapp.databinding.FragmentStatsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class StatsFragment: Fragment() {
    private lateinit var binding: FragmentStatsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = activity?.intent?.getIntExtra("id", 0) ?: 0
        if (id != 0) {
            getAbility(id)
            getEggGroup(id)
            getGender(id)
        }
    }

    private fun getEggGroup(id: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(PokemonService::class.java)
        service.getEggGroups(id).enqueue(object : Callback<EggGroupsResponse> {
            override fun onResponse(
                call: Call<EggGroupsResponse>,
                response: Response<EggGroupsResponse>
            ) {
                try {
                    val data = response.body()
                    binding.tvEggGroup.text = data?.name
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<EggGroupsResponse>, t: Throwable) {
            }
        })
    }

    private fun getGender(id: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(PokemonService::class.java)
        service.getGender(id).enqueue(object : Callback<GenderResponse> {
            override fun onResponse(
                call: Call<GenderResponse>,
                response: Response<GenderResponse>
            ) {
                try {
                    val data = response.body()
                    binding.tvGender.text = data?.name
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<GenderResponse>, t: Throwable) {
            }
        })
    }

    private fun getAbility(id: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(PokemonService::class.java)
        service.getAbility(id).enqueue(object : Callback<AbilityResponse> {
            override fun onResponse(
                call: Call<AbilityResponse>,
                response: Response<AbilityResponse>
            ) {
                try {
                    val data = response.body()
                    val effects = data?.effect_entries
                    effects?.find { it.language?.name == "en" }?.let {
                        binding.tvTitleAb.text = it.short_effect ?: "-"
                        binding.tvDescAb.text = it.effect ?: "-"
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<AbilityResponse>, t: Throwable) {
            }
        })
    }
}