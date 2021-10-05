package io.github.dzulfikar68.pokeapp.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {
    @GET("pokemon-species/")
    fun getPokemon(): Call<PokemonResponse>

    @GET("pokemon-species/{id}")
    fun getPokemonDetail(@Path("id") id: Int): Call<PokemonSpecies>

    @GET("evolution-chain/{id}")
    fun getEvolutionChain(@Path("id") id: Int): Call<EvolutionsResponse>

    @GET("ability/{id}")
    fun getAbility(@Path("id") id: Int): Call<AbilityResponse>

    @GET("gender/{id}")
    fun getGender(@Path("id") id: Int): Call<GenderResponse>

    @GET("pokemon-form/{name}")
    fun getForm(@Path("name") name: String): Call<FormResponse>

    //=============================================================================

    @GET("characteristic/{id}")
    fun getPokemonChar(@Path("id") id: Int): Call<CharPokemonResponse>

    @GET("egg-group/{id}")
    fun getEggGroups(@Path("id") id: Int): Call<EggGroupsResponse>

    //====================================================================

    @GET("evolution-trigger/{id}")
    fun getEvolutionTriggers(@Path("id") id: Int): Call<EvolutionsResponse>

//    @GET("stat/{id}")
//    fun getStats(@Path("id") id: Int): Call<StatsResponse>
}