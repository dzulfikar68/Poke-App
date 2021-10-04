package io.github.dzulfikar68.pokeapp

data class ItemPokemon(
    val name: String? = null,
    val url: String? = null
)

data class PokemonResponse(
    val results: List<ItemPokemon>? = null
)