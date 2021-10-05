package io.github.dzulfikar68.pokeapp.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PokemonRepository constructor(private val remoteDataSource: RemoteDataSource): PokemonDataSource {
    companion object {
        @Volatile
        private var instance: PokemonRepository? = null
        fun getInstance(remoteData: RemoteDataSource): PokemonRepository =
                instance ?: synchronized(this) {
                    instance ?: PokemonRepository(remoteData).apply { instance = this }
                }
    }

    override fun getPokemonList(): LiveData<MainResponse<List<ItemPokemon>>> {
        val movieResult = MutableLiveData<MainResponse<List<ItemPokemon>>>()
        remoteDataSource.getPokemonList(object : RemoteDataSource.PokemonListCallback {
            override fun onPokemonListReceived(pokemonResponses: MainResponse<List<ItemPokemon>>) {
                movieResult.postValue(pokemonResponses)
            }
        })
        return movieResult
    }
}