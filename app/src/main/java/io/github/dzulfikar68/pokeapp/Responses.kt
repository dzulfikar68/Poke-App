package io.github.dzulfikar68.pokeapp

data class ItemPokemon(
    val name: String? = null,
    val url: String? = null
)

data class PokemonResponse(
    val results: List<ItemPokemon>? = null
)

data class CharPokemonResponse(
    val descriptions: List<CharPokemon>? = null,
)

data class CharPokemon(
    val description: String? = null,
    val language: Language? = null
)

data class Language(
    val name: String? = null
)

data class EvolutionsResponse(
    val chain: ChainEvo? = null,
)

data class ChainEvo(
    val species: ItemPokemon? = null,
    val evolves_to: List<ChainEvo>? = null,
    val evolution_details: List<DetailEvo>? = null
)

data class DetailEvo(
    val trigger: ItemPokemon? = null,
    val min_level: Int? = null
)

//data class StatsResponse(
//)