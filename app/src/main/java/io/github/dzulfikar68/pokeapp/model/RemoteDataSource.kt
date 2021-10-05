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

    fun getPokemonDetail(id:Int, callback: PokemonDetailCallback) {
        services.getPokemonDetail(id).enqueue(object: Callback<PokemonSpecies> {
            override fun onResponse(
                call: Call<PokemonSpecies>,
                response: Response<PokemonSpecies>
            ) {
                val data = response.body()
                val res = MainResponse(
                    isError = false,
                    message = response.message(),
                    data = data
                )
                callback.onPokemonDetailReceived(res)
            }
            override fun onFailure(call: Call<PokemonSpecies>, t: Throwable) {
                val res = MainResponse<PokemonSpecies>(
                    isError = true,
                    message = t.localizedMessage,
                    data = null
                )
                callback.onPokemonDetailReceived(res)
            }
        })
    }

    fun getPokemonForm(name: String, callback: PokemonFormCallback) {
        services.getForm(name).enqueue(object: Callback<FormResponse> {
            override fun onResponse(
                call: Call<FormResponse>,
                response: Response<FormResponse>
            ) {
                val data = response.body()
                val res = MainResponse(
                    isError = false,
                    message = response.message(),
                    data = data
                )
                callback.onPokemonFormReceived(res)
            }
            override fun onFailure(call: Call<FormResponse>, t: Throwable) {
                val res = MainResponse<FormResponse>(
                    isError = true,
                    message = t.localizedMessage,
                    data = null
                )
                callback.onPokemonFormReceived(res)
            }
        })
    }

    fun getPokemonEvolutions(id: Int, callback: PokemonEvolutionsCallback) {
        services.getEvolutionChain(id).enqueue(object: Callback<EvolutionsResponse> {
            override fun onResponse(
                call: Call<EvolutionsResponse>,
                response: Response<EvolutionsResponse>
            ) {
                val data = response.body()
                val res = MainResponse(
                    isError = false,
                    message = response.message(),
                    data = data
                )
                callback.onPokemonEvolutionsReceived(res)
            }
            override fun onFailure(call: Call<EvolutionsResponse>, t: Throwable) {
                val res = MainResponse<EvolutionsResponse>(
                    isError = true,
                    message = t.localizedMessage,
                    data = null
                )
                callback.onPokemonEvolutionsReceived(res)
            }
        })
    }

    interface PokemonListCallback {
        fun onPokemonListReceived(pokemonResponses: MainResponse<List<ItemPokemon>>)
    }
    interface PokemonDetailCallback {
        fun onPokemonDetailReceived(pokemonResponses: MainResponse<PokemonSpecies>)
    }
    interface PokemonFormCallback {
        fun onPokemonFormReceived(pokemonResponses: MainResponse<FormResponse>)
    }
    interface PokemonEvolutionsCallback {
        fun onPokemonEvolutionsReceived(pokemonResponses: MainResponse<EvolutionsResponse>)
    }
}