package com.kforeach.pokedex.models

data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<String>,
    val heightM: Float,
    val weightKg: Float,
    val moves: List<String>,
    val hp: Int,
    val atk: Int,
    val def: Int,
    val satk: Int,
    val sdef: Int,
    val spd: Int
) {
    companion object {
        fun getPreviewPokemon(): Pokemon {
            return Pokemon(
                id = 2,
                name = "Ivysaur",
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/2.png",
                types = listOf("Grass", "Poison"),
                heightM = 1.0f,
                weightKg = 13.0f,
                moves = listOf(
                    "swords-dance",
                    "cut"
                ),
                hp = 60,
                atk = 62,
                def = 63,
                satk = 80,
                sdef = 80,
                spd = 60
            )
        }
    }
}

fun PokemonDetail.mapToPokemon(): Pokemon {
    val typesList = types.map { it.type.name.replaceFirstChar { char -> char.uppercase() } }

    val image = sprites?.other?.officialArtwork?.frontDefault
        ?: sprites?.frontDefault
        ?: ""

    val heightM = height / 10f
    val weightKg = weight / 10f

    val movesList = moves.take(2).map { it.move.name }

    fun getStat(statName: String): Int {
        return stats.firstOrNull { it.stat.name == statName }?.baseStat ?: 0
    }

    return Pokemon(
        id = id,
        name = name.replaceFirstChar { it.uppercase() },
        imageUrl = image,
        types = typesList,
        heightM = heightM,
        weightKg = weightKg,
        moves = movesList,
        hp = getStat("hp"),
        atk = getStat("attack"),
        def = getStat("defense"),
        satk = getStat("special-attack"),
        sdef = getStat("special-defense"),
        spd = getStat("speed")
    )
}
