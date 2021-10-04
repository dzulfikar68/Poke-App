package io.github.dzulfikar68.pokeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.dzulfikar68.pokeapp.databinding.FragmentEvolutionsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class EvolutionsFragment : Fragment() {

    private lateinit var binding: FragmentEvolutionsBinding
    private lateinit var evoAdapter: EvoAdapter

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

        evoAdapter = EvoAdapter()
        with(binding.rvEvolutions) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = evoAdapter
        }

        getDetailItem()
    }

    private fun getDetailItem() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(PokemonService::class.java)
        val id = activity?.intent?.getIntExtra("id", 0) ?: 0
        service.getEvolutionChain(id).enqueue(object : Callback<EvolutionsResponse> {
            override fun onResponse(
                call: Call<EvolutionsResponse>,
                response: Response<EvolutionsResponse>
            ) {
                val data = response.body()
                val list = data?.chain?.evolves_to ?: listOf()
                evoAdapter.setList(list)
            }

            override fun onFailure(call: Call<EvolutionsResponse>, t: Throwable) {
//                Toast.makeText(
//                    context,
//                    "Terjadi Kesalahan",
//                    Toast.LENGTH_SHORT
//                ).show()
            }
        })
    }
}