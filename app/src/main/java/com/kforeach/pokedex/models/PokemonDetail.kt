package com.kforeach.pokedex.models

import com.google.gson.annotations.SerializedName

data class PokemonDetail(
    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("name")
    val name: String = "",

    @SerializedName("height")
    val height: Int = 0,

    @SerializedName("weight")
    val weight: Int = 0,

    @SerializedName("types")
    val types: List<PokemonTypeSlot> = emptyList(),

    @SerializedName("stats")
    val stats: List<PokemonStatSlot> = emptyList(),

    @SerializedName("moves")
    val moves: List<PokemonMoveSlot> = emptyList(),

    @SerializedName("sprites")
    val sprites: PokemonSprites? = null
)

data class PokemonTypeSlot(
    @SerializedName("slot")
    val slot: Int = 0,

    @SerializedName("type")
    val type: PokemonType = PokemonType()
)

data class PokemonType(
    @SerializedName("name")
    val name: String = ""
)

data class PokemonStatSlot(
    @SerializedName("base_stat")
    val baseStat: Int = 0,

    @SerializedName("effort")
    val effort: Int = 0,

    @SerializedName("stat")
    val stat: PokemonStat = PokemonStat()
)

data class PokemonStat(
    @SerializedName("name")
    val name: String = ""
)

data class PokemonMoveSlot(
    @SerializedName("move")
    val move: PokemonMove = PokemonMove()
)

data class PokemonMove(
    @SerializedName("name")
    val name: String = ""
)

data class PokemonSprites(
    @SerializedName("front_default")
    val frontDefault: String? = null,

    @SerializedName("other")
    val other: OtherSprites? = null
)

data class OtherSprites(
    @SerializedName("official-artwork")
    val officialArtwork: OfficialArtwork? = null
)

data class OfficialArtwork(
    @SerializedName("front_default")
    val frontDefault: String? = null
)