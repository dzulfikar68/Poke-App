package io.github.dzulfikar68.pokeapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.dzulfikar68.pokeapp.model.*

class DetailViewModel(private val pokemonRepository: PokemonRepository) : ViewModel() {
    var pokemonDetail : LiveData<MainResponse<PokemonSpecies>>? = null
    fun getPokemonDetail(id: Int) = run { pokemonDetail = pokemonRepository.getPokemonDetail(id) }

    var pokemonForm : LiveData<MainResponse<FormResponse>>? = null
    fun getPokemonForm(name: String) = run { pokemonForm = pokemonRepository.getPokemonForm(name) }

    var pokemonEvolutions : LiveData<MainResponse<EvolutionsResponse>>? = null
    fun getPokemonEvolutions(id: Int) = run { pokemonEvolutions = pokemonRepository.getPokemonEvolutions(id) }

    var pokemonAbility : LiveData<MainResponse<AbilityResponse>>? = null
    fun getPokemonAbility(id: Int) = run { pokemonAbility = pokemonRepository.getPokemonAbility(id) }

    var pokemonGender = MutableLiveData<MainResponse<GenderResponse>>()
    fun getPokemonGender(id: Int) = run { pokemonRepository.getPokemonGender(id) {
        pokemonGender.postValue(it)
    } }
}