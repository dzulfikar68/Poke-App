package io.github.dzulfikar68.pokeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.github.dzulfikar68.pokeapp.model.*

class DetailViewModel(private val pokemonRepository: PokemonRepository) : ViewModel() {
    var pokemonDetail : LiveData<MainResponse<PokemonSpecies>>? = null
    fun getPokemonDetail(id: Int) = run { pokemonDetail = pokemonRepository.getPokemonDetail(id) }

    var pokemonForm : LiveData<MainResponse<FormResponse>>? = null
    fun getPokemonForm(name: String) = run { pokemonForm = pokemonRepository.getPokemonForm(name) }

    var pokemonEvolutions : LiveData<MainResponse<EvolutionsResponse>>? = null
    fun getPokemonEvolutions(id: Int) = run { pokemonEvolutions = pokemonRepository.getPokemonEvolutions(id) }
}