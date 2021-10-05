package io.github.dzulfikar68.pokeapp.model

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor(private val services: PokemonService){
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(services: PokemonService): RemoteDataSource =
                instance ?: synchronized(this) {
                    instance ?: RemoteDataSource(services).apply { instance = this }
                }
    }

    fun getPokemonList(callback: PokemonListCallback) {
        services.getPokemon().enqueue(object: Callback<PokemonResponse> {
            override fun onResponse(
                    call: Call<PokemonResponse>,
                    response: Response<PokemonResponse>
            ) {
                val data = response.body()
                val listItem = data?.results ?: listOf()
                val res = MainResponse(
                    isError = false,
                    message = response.message(),
                    data = listItem
                )
                callback.onPokemonListReceived(res)
            }
            override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {
                val res = MainResponse<List<ItemPokemon>>(
                    isError = true,
                    message = t.localizedMessage,
                    data = null
                )
                callback.onPokemonListReceived(res)
            }
        })
    }

    interface PokemonListCallback {
        fun onPokemonListReceived(pokemonResponses: MainResponse<List<ItemPokemon>>)
    }
}