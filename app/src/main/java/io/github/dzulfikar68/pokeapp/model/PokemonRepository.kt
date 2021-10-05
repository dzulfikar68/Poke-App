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
        val result = MutableLiveData<MainResponse<List<ItemPokemon>>>()
        remoteDataSource.getPokemonList(object : RemoteDataSource.PokemonListCallback {
            override fun onPokemonListReceived(pokemonResponses: MainResponse<List<ItemPokemon>>) {
                result.postValue(pokemonResponses)
            }
        })
        return result
    }

    override fun getPokemonDetail(id: Int): LiveData<MainResponse<PokemonSpecies>> {
        val result = MutableLiveData<MainResponse<PokemonSpecies>>()
        remoteDataSource.getPokemonDetail(id, object : RemoteDataSource.PokemonDetailCallback {
            override fun onPokemonDetailReceived(pokemonResponses: MainResponse<PokemonSpecies>) {
                result.postValue(pokemonResponses)
            }
        })
        return result
    }

    override fun getPokemonForm(name: String): LiveData<MainResponse<FormResponse>> {
        val result = MutableLiveData<MainResponse<FormResponse>>()
        remoteDataSource.getPokemonForm(name, object : RemoteDataSource.PokemonFormCallback {
            override fun onPokemonFormReceived(pokemonResponses: MainResponse<FormResponse>) {
                result.postValue(pokemonResponses)
            }
        })
        return result
    }

    override fun getPokemonEvolutions(id: Int): LiveData<MainResponse<EvolutionsResponse>> {
        val result = MutableLiveData<MainResponse<EvolutionsResponse>>()
        remoteDataSource.getPokemonEvolutions(id, object : RemoteDataSource.PokemonEvolutionsCallback {
            override fun onPokemonEvolutionsReceived(pokemonResponses: MainResponse<EvolutionsResponse>) {
                result.postValue(pokemonResponses)
            }
        })
        return result
    }

    override fun getPokemonAbility(id: Int): LiveData<MainResponse<AbilityResponse>> {
        val result = MutableLiveData<MainResponse<AbilityResponse>>()
        remoteDataSource.getPokemonAbility(id, object : RemoteDataSource.PokemonAbilityCallback {
            override fun onPokemonAbilityReceived(pokemonResponses: MainResponse<AbilityResponse>) {
                result.postValue(pokemonResponses)
            }
        })
        return result
    }

    override fun getPokemonGender(id: Int, callback: (MainResponse<GenderResponse>) -> Unit) {
        remoteDataSource.getPokemonGender(id, object : RemoteDataSource.PokemonGenderCallback {
            override fun onPokemonGenderReceived(pokemonResponses: MainResponse<GenderResponse>) {
                callback.invoke(pokemonResponses)
            }
        })
    }

}