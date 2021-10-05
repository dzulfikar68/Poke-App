package io.github.dzulfikar68.pokeapp

import android.content.Context
import io.github.dzulfikar68.pokeapp.model.PokemonRepository
import io.github.dzulfikar68.pokeapp.model.PokemonService
import io.github.dzulfikar68.pokeapp.model.RemoteDataSource
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

object Injection {
    fun provideRepository(context: Context): PokemonRepository {
        val remoteDataSource = RemoteDataSource.getInstance(RetrofitClient.movieServices())
        return PokemonRepository.getInstance(remoteDataSource)
    }
}

object RetrofitClient {
    private const val URL = "https://pokeapi.co/api/v2/"
    fun movieServices(): PokemonService {
        val retrofit =  Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit.create(PokemonService::class.java)
    }
}

fun String.capitalizeWords(): String = split(" ").map { it.capitalize(Locale.getDefault()) }.joinToString(" ")
