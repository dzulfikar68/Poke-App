package io.github.dzulfikar68.pokeapp

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.dzulfikar68.pokeapp.databinding.ItemEvolutionsViewBinding

class EvoAdapter : RecyclerView.Adapter<EvoAdapter.ViewHolder>() {
    var list = ArrayList<ChainEvo>()

    fun setList(lists: List<ChainEvo>) {
        this.list.clear()
        this.list.addAll(lists)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val adapterBinding = ItemEvolutionsViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(adapterBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lists = list[position]
        holder.bind(lists)
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(private val binding: ItemEvolutionsViewBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: ChainEvo) {
            binding.tvEvo1.text = item.species?.name ?: "-"
            binding.tvEvo2.text = try {
                item.evolves_to?.get(0)?.species?.name ?: "-"
            } catch (e: Exception) {
                "-"
            }
            val lv = try {
                (item.evolves_to?.get(0)?.evolution_details?.get(0)?.min_level ?: 0).toString()
            } catch (e: Exception) {
                "0"
            }
            binding.tvLevel.text = "Lv. $lv"
        }
    }
}