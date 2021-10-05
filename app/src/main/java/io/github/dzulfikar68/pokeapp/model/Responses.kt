package io.github.dzulfikar68.pokeapp.model

data class MainResponse<T>(
    val isError: Boolean = false,
    val message: String? = null,
    val data: T? = null
)

data class ItemPokemon(
    val name: String? = null,
    val url: String? = null
)

data class PokemonResponse(
    val results: List<ItemPokemon>? = null
)

data class PokemonItemResponse(
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

data class FormResponse(
    val sprites: Sprites? = null
)

data class Sprites(
    val front_default: String? = null,
    val front_shiny: String? = null
)

data class AbilityResponse(
    val effect_entries: List<EffectEntries>? = null
)

data class EffectEntries(
    val short_effect: String? = null,
    val effect: String? = null,
    val language: Language? = null
)

data class EggGroupsResponse(
    val id: Int? = null,
    val name: String? = null
)

data class GenderResponse(
    val id: Int? = null,
    val name: String? = null
)

//data class StatsResponse(
//)