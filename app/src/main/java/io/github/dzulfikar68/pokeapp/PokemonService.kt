package io.github.dzulfikar68.pokeapp

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {
    @GET("pokemon-species/")
    fun getPokemon(): Call<PokemonResponse>

    @GET("characteristic/{id}")
    fun getPokemonChar(@Path("id") id: Int): Call<CharPokemonResponse>

    @GET("pokemon-form/{name}")
    fun getForm(@Path("name") name: String): Call<FormResponse>

    @GET("evolution-chain/{id}")
    fun getEvolutionChain(@Path("id") id: Int): Call<EvolutionsResponse>

    @GET("ability/{id}")
    fun getAbility(@Path("id") id: Int): Call<AbilityResponse>

    @GET("egg-group/{id}")
    fun getEggGroups(@Path("id") id: Int): Call<EggGroupsResponse>

    @GET("gender/{id}")
    fun getGender(@Path("id") id: Int): Call<GenderResponse>

    //====================================================================

    @GET("evolution-trigger/{id}")
    fun getEvolutionTriggers(@Path("id") id: Int): Call<EvolutionsResponse>

//    @GET("stat/{id}")
//    fun getStats(@Path("id") id: Int): Call<StatsResponse>
}