package io.github.dzulfikar68.pokeapp.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.github.dzulfikar68.pokeapp.capitalizeWords
import io.github.dzulfikar68.pokeapp.databinding.ItemEvolutionsViewBinding
import io.github.dzulfikar68.pokeapp.model.ChainEvo
import io.github.dzulfikar68.pokeapp.model.FormResponse
import io.github.dzulfikar68.pokeapp.model.PokemonService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EvolutionsAdapter : RecyclerView.Adapter<EvolutionsAdapter.ViewHolder>() {

    var list = ArrayList<ChainEvo>()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(lists: List<ChainEvo>) {
        this.list.clear()
        this.list.addAll(lists)
        this.notifyDataSetChanged()
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
            val text1 = item.species?.name ?: "-"
            binding.tvEvo1.text = text1.capitalizeWords()
            if (text1 != "-") getImage(text1, binding.ivEvo1)

            val text2 = try {
                item.evolves_to?.get(0)?.species?.name ?: "-"
            } catch (e: Exception) {
                "-"
            }
            binding.tvEvo2.text = text2.capitalizeWords()
            if (text2 != "-") getImage(text2, binding.ivEvo2)

            val lv = try {
                (item.evolves_to?.get(0)?.evolution_details?.get(0)?.min_level ?: 0).toString()
            } catch (e: Exception) {
                "0"
            }
            binding.tvLevel.text = "Min Lv. $lv"
        }

        private fun getImage(name: String, imageView: ImageView) {
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
                            .into(imageView)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(call: Call<FormResponse>, t: Throwable) {
                }
            })
        }
    }

}
