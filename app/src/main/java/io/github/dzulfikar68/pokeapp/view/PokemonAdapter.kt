package io.github.dzulfikar68.pokeapp.view

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.github.dzulfikar68.pokeapp.EspressoIdlingResource
import io.github.dzulfikar68.pokeapp.capitalizeWords
import io.github.dzulfikar68.pokeapp.model.FormResponse
import io.github.dzulfikar68.pokeapp.model.ItemPokemon
import io.github.dzulfikar68.pokeapp.model.PokemonService
import io.github.dzulfikar68.pokeapp.databinding.ItemPokemonViewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonAdapter : RecyclerView.Adapter<PokemonAdapter.MessageViewHolder>() {

    var list = ArrayList<ItemPokemon>()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(messages: List<ItemPokemon>) {
        this.list.clear()
        this.list.addAll(messages)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val messageAdapterBinding = ItemPokemonViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(messageAdapterBinding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val list = list[position]
        holder.bind(list)
    }

    override fun getItemCount(): Int = list.size

    class MessageViewHolder(private val binding: ItemPokemonViewBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: ItemPokemon) {
            println("@poke" + item)
            binding.tvTitle.text = item.name?.capitalizeWords()
            var pokemonId = 0
            try {
                val listString = item.url?.split("/")
                pokemonId = (listString?.get(6) ?: "0").toInt()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            binding.tvDesc.text = "#$pokemonId"
            itemView.setOnClickListener {
                val ctx = binding.root.context
                ctx.startActivity(
                    Intent(ctx, DetailActivity::class.java)
                        .putExtra("id", pokemonId)
                        .putExtra("name", item.name)
                        .putExtra("url", item.url)
                )
            }
            item.name?.let {
                getImage(it)
            }
        }

        private fun getImage(name: String) {
            EspressoIdlingResource.increment()
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
                    try {
                        val image = response.body()?.sprites?.front_default
                        Glide.with(binding.root.context)
                            .load(image)
                            .into(binding.ivPoke)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<FormResponse>, t: Throwable) {
                    EspressoIdlingResource.decrement()
                }
            })
        }
    }

}
