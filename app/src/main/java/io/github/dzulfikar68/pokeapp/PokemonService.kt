package io.github.dzulfikar68.pokeapp

import retrofit2.Call
import retrofit2.http.GET

interface PokemonService {
    @GET("pokemon/")
    fun getPokemon(): Call<PokemonResponse>
}