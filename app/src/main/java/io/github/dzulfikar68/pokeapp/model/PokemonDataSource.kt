package io.github.dzulfikar68.pokeapp.model

import androidx.lifecycle.LiveData

interface PokemonDataSource {
    fun getPokemonList(): LiveData<MainResponse<List<ItemPokemon>>>
}