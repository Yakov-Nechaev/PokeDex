package com.kforeach.pokedex.data

import com.kforeach.pokedex.models.PokemonDetail
import com.kforeach.pokedex.models.PokemonDex
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

//const val URL_MAIN = "https://pokeapi.co/api/v2/"
//
//object NetworkService {
//    private val client = OkHttpClient.Builder()
//        .connectTimeout(90, TimeUnit.SECONDS)
//        .readTimeout(90, TimeUnit.SECONDS)
//        .writeTimeout(90, TimeUnit.SECONDS)
//        .build()
//
//    private val retrofit = Retrofit.Builder()
//        .baseUrl(URL_MAIN)
//        .addConverterFactory(GsonConverterFactory.create())
//        .client(client)
//        .build()
//
//    val apiService: ApiService = retrofit.create(ApiService::class.java)
//}


interface ApiService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit:Int,
        @Query("offset") offset:Int
    ): PokemonDex

    @GET("pokemon/{idOrName}")
    suspend fun getPokemonInfo(
        @Path("idOrName") idOrName: String
    ): PokemonDetail
}