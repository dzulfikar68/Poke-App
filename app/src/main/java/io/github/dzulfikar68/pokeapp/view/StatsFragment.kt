package io.github.dzulfikar68.pokeapp.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import io.github.dzulfikar68.pokeapp.capitalizeWords
import io.github.dzulfikar68.pokeapp.databinding.FragmentStatsBinding
import io.github.dzulfikar68.pokeapp.model.*
import io.github.dzulfikar68.pokeapp.viewmodel.DetailViewModel
import io.github.dzulfikar68.pokeapp.viewmodel.ViewModelFactory
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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            val id = activity?.intent?.getIntExtra("id", 0) ?: 0
            val name = activity?.intent?.getStringExtra("name") ?: ""

            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(it, factory)[DetailViewModel::class.java]

            viewModel.pokemonDetail?.observe(requireActivity(), {
                if (!it.isError) {
                    val data = it.data
                    binding.tvGeneration.text = data?.generation?.name?.replace("-", " ")?.capitalizeWords()?.trim()
                    binding.tvHabitat.text = data?.habitat?.name?.replace("-", " ")?.capitalizeWords()
                    val hatch = data?.hatch_counter.toString()
                    binding.tvHatchTime.text = "$hatch Cycles"
                    val rate = data?.capture_rate.toString()
                    binding.tvCaptureRate.text = "$rate%"
                    var eggGroup = ""
                    data?.egg_groups?.forEach {
                        eggGroup = eggGroup+it.name?.capitalizeWords()+"\n"
                    }
                    binding.tvEggGroup.text = eggGroup.trim()
                }
            })
            viewModel.pokemonForm?.observe(requireActivity(), {
                if (!it.isError) {
                    val data = it.data
                    Glide.with(binding.root.context)
                        .load(data?.sprites?.front_default)
                        .into(binding.ivNormal)
                    Glide.with(binding.root.context)
                        .load(data?.sprites?.front_shiny)
                        .into(binding.ivShiny)
                }
            })
            viewModel.pokemonAbility?.observe(requireActivity(), {
                if (!it.isError) {
                    try {
                        val data = it.data
                        val effects = data?.effect_entries
                        effects?.find { it.language?.name == "en" }?.let {
                            binding.tvTitleAb.text = it.short_effect ?: "-"
                            binding.tvDescAb.text = it.effect ?: "-"
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            })
            viewModel.pokemonGender?.observe(requireActivity(), {
                if (!it.isError) {
                    try {
                        val data = it.data
                        binding.tvGender.text = data?.name?.capitalizeWords() ?: "-"
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            })
        }
    }
}