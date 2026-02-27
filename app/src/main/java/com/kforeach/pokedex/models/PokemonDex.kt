package com.kforeach.pokedex.models

data class PokemonDex(
    val count: Int,
    val results: List<PokemonShort> = listOf()
)

data class PokemonShort(
    val name: String = "",
    val url: String = ""
) {

    companion object {
        fun previewPokemonShort(): PokemonShort {
            return PokemonShort(
                name = listOf("pikachu", "charmander", "bulbasaur", "squirtle", "pidgey").random(),
                url = "https://pokeapi.co/api/v2/pokemon/${(1..1000).random()}/"
            )
        }
    }

}


fun PokemonShort.getPokemonId(): String {
    return url.trimEnd('/').substringAfterLast('/')
}

fun PokemonShort.getImageUrl(): String {
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${getPokemonId()}.png"
}