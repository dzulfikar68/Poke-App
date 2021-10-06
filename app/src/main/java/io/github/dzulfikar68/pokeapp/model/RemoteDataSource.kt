package io.github.dzulfikar68.pokeapp.model

import io.github.dzulfikar68.pokeapp.EspressoIdlingResource
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
        EspressoIdlingResource.increment()
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
                EspressoIdlingResource.decrement()
            }
            override fun onFailure(call: Call<PokemonResponse>, t: Throwable) {
                val res = MainResponse<List<ItemPokemon>>(
                    isError = true,
                    message = t.localizedMessage,
                    data = null
                )
                callback.onPokemonListReceived(res)
                EspressoIdlingResource.decrement()
            }
        })
    }

    fun getPokemonDetail(id:Int, callback: PokemonDetailCallback) {
        EspressoIdlingResource.increment()
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
                EspressoIdlingResource.decrement()
            }
            override fun onFailure(call: Call<PokemonSpecies>, t: Throwable) {
                val res = MainResponse<PokemonSpecies>(
                    isError = true,
                    message = t.localizedMessage,
                    data = null
                )
                callback.onPokemonDetailReceived(res)
                EspressoIdlingResource.decrement()
            }
        })
    }

    fun getPokemonForm(name: String, callback: PokemonFormCallback) {
        EspressoIdlingResource.increment()
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
                EspressoIdlingResource.decrement()
            }
            override fun onFailure(call: Call<FormResponse>, t: Throwable) {
                val res = MainResponse<FormResponse>(
                    isError = true,
                    message = t.localizedMessage,
                    data = null
                )
                callback.onPokemonFormReceived(res)
                EspressoIdlingResource.decrement()
            }
        })
    }

    fun getPokemonEvolutions(id: Int, callback: PokemonEvolutionsCallback) {
        EspressoIdlingResource.increment()
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
                EspressoIdlingResource.decrement()
            }
            override fun onFailure(call: Call<EvolutionsResponse>, t: Throwable) {
                val res = MainResponse<EvolutionsResponse>(
                    isError = true,
                    message = t.localizedMessage,
                    data = null
                )
                callback.onPokemonEvolutionsReceived(res)
                EspressoIdlingResource.decrement()
            }
        })
    }

    fun getPokemonAbility(id: Int, callback: PokemonAbilityCallback) {
        EspressoIdlingResource.increment()
        services.getAbility(id).enqueue(object: Callback<AbilityResponse> {
            override fun onResponse(
                call: Call<AbilityResponse>,
                response: Response<AbilityResponse>
            ) {
                val data = response.body()
                val res = MainResponse(
                    isError = false,
                    message = response.message(),
                    data = data
                )
                callback.onPokemonAbilityReceived(res)
                EspressoIdlingResource.decrement()
            }
            override fun onFailure(call: Call<AbilityResponse>, t: Throwable) {
                val res = MainResponse<AbilityResponse>(
                    isError = true,
                    message = t.localizedMessage,
                    data = null
                )
                callback.onPokemonAbilityReceived(res)
                EspressoIdlingResource.decrement()
            }
        })
    }

    fun getPokemonGender(id: Int, callback: PokemonGenderCallback) {
        EspressoIdlingResource.increment()
        services.getGender(id).enqueue(object: Callback<GenderResponse> {
            override fun onResponse(
                call: Call<GenderResponse>,
                response: Response<GenderResponse>
            ) {
                val data = response.body()
                val res = MainResponse(
                    isError = false,
                    message = response.message(),
                    data = data
                )
                callback.onPokemonGenderReceived(res)
                EspressoIdlingResource.decrement()
            }
            override fun onFailure(call: Call<GenderResponse>, t: Throwable) {
                val res = MainResponse<GenderResponse>(
                    isError = true,
                    message = t.localizedMessage,
                    data = null
                )
                callback.onPokemonGenderReceived(res)
                EspressoIdlingResource.decrement()
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

    interface PokemonAbilityCallback {
        fun onPokemonAbilityReceived(pokemonResponses: MainResponse<AbilityResponse>)
    }

    interface PokemonGenderCallback {
        fun onPokemonGenderReceived(pokemonResponses: MainResponse<GenderResponse>)
    }

}
