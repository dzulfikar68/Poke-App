package io.github.dzulfikar68.pokeapp

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.github.dzulfikar68.pokeapp.databinding.ItemPokemonViewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokemonAdapter : RecyclerView.Adapter<PokemonAdapter.MessageViewHolder>() {
    var list = ArrayList<ItemPokemon>()

    fun setList(messages: List<ItemPokemon>) {
        this.list.clear()
        this.list.addAll(messages)
        notifyDataSetChanged()
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
            binding.tvTitle.text = item.name
            binding.tvDesc.text = item.url
            var idPokemeon = 0
            try {
                val listString = item.url?.split("/")
                idPokemeon = (listString?.get(6) ?: "0").toInt()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            itemView.setOnClickListener {
                val ctx = binding.root.context
                ctx.startActivity(
                    Intent(ctx, DetailActivity::class.java)
                        .putExtra("id", idPokemeon)
                        .putExtra("name", item.name)
                        .putExtra("url", item.url)
                )
            }
            item.name?.let {
                getImage(it)
            }
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
}