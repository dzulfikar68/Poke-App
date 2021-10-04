package io.github.dzulfikar68.pokeapp

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.dzulfikar68.pokeapp.databinding.ItemPokemonViewBinding

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
            itemView.setOnClickListener {
                val ctx = binding.root.context
                ctx.startActivity(
                    Intent(ctx, DetailActivity::class.java)
                        .putExtra("name", item.name)
                )
            }
        }
    }
}