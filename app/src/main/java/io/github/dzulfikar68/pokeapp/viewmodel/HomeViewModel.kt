package io.github.dzulfikar68.pokeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.github.dzulfikar68.pokeapp.model.ItemPokemon
import io.github.dzulfikar68.pokeapp.model.MainResponse
import io.github.dzulfikar68.pokeapp.model.PokemonRepository

class HomeViewModel(private val pokemonRepository: PokemonRepository) : ViewModel() {

    fun getPokemonList(): LiveData<MainResponse<List<ItemPokemon>>> = pokemonRepository.getPokemonList()

}
