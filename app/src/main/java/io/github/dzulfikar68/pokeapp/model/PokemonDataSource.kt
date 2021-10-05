package io.github.dzulfikar68.pokeapp.model

import androidx.lifecycle.LiveData

interface PokemonDataSource {
    fun getPokemonList(): LiveData<MainResponse<List<ItemPokemon>>>
    fun getPokemonDetail(id: Int): LiveData<MainResponse<PokemonSpecies>>
    fun getPokemonForm(name: String): LiveData<MainResponse<FormResponse>>
    fun getPokemonEvolutions(id: Int): LiveData<MainResponse<EvolutionsResponse>>
}