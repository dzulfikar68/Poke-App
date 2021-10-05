package io.github.dzulfikar68.pokeapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.dzulfikar68.pokeapp.databinding.FragmentEvolutionsBinding
import io.github.dzulfikar68.pokeapp.model.EvolutionsResponse
import io.github.dzulfikar68.pokeapp.model.PokemonService
import io.github.dzulfikar68.pokeapp.viewmodel.DetailViewModel
import io.github.dzulfikar68.pokeapp.viewmodel.ViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EvolutionsFragment : Fragment() {

    private lateinit var binding: FragmentEvolutionsBinding
    private lateinit var evoAdapter: EvolutionsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEvolutionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            val factory = ViewModelFactory.getInstance(requireActivity())
            val viewModel = ViewModelProvider(it, factory)[DetailViewModel::class.java]

            evoAdapter = EvolutionsAdapter()
            with(binding.rvEvolutions) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = evoAdapter
            }

            viewModel.pokemonEvolutions?.observe(requireActivity(), {
                if (!it.isError) {
                    val list = it?.data?.chain?.evolves_to ?: listOf()
                    evoAdapter.setList(list)
                }
            })
        }
    }
}