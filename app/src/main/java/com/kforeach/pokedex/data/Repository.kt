package com.kforeach.pokedex.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.google.gson.GsonBuilder
import com.kforeach.pokedex.models.Pokemon
import com.kforeach.pokedex.models.PokemonDex
import com.kforeach.pokedex.models.getPokemonId
import com.kforeach.pokedex.models.mapToPokemon
import com.kforeach.pokedex.util.LoggerObj
import com.kforeach.pokedex.util.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(
    val context: Context,
    val network: ApiService,
    val store: StoreManager,
) {
    suspend fun getPokemonList(limit: Int, offset: Int): ResultWrapper<PokemonDex> {
        return withContext(Dispatchers.IO) {
            try {
                if (!isInternetAvailable()) {
                    throw Exception("No hay conexión a internet.")
                }
                val response = network.getPokemonList(limit = limit, offset = offset)

                LoggerObj.i(
                    message = response.results.take(10).joinToString(separator = "\n") { pokemonShort ->
                        "Name = ${pokemonShort.name}, ID = ${pokemonShort.getPokemonId()}"
                    },
                    methodName = "getPokemonList"
                )

                ResultWrapper.success(data = response)
            } catch (e: Exception) {
                LoggerObj.e(throwable = e, methodName = "getPokemonList")
                ResultWrapper.error(error = e, message = e.message.toString())
            }
        }
    }

    suspend fun getPokemon(idOrName: String): ResultWrapper<Pokemon> {
        return withContext(Dispatchers.IO) {
            try {
                if (!isInternetAvailable()) {
                    throw Exception("No hay conexión a internet.")
                }
                val response = network.getPokemonInfo(idOrName = idOrName).mapToPokemon()

                LoggerObj.i(
                    message = GsonBuilder().setPrettyPrinting().create().toJson(response),
                    methodName = "getPokemon"
                )

                ResultWrapper.success(data = response)
            } catch (e: Exception) {
                LoggerObj.e(throwable = e, methodName = "getPokemon")
                ResultWrapper.error(error = e, message = e.message.toString())
            }
        }
    }

    fun isFirstLaunch(): Boolean = store.isFirstLaunch()

    fun setFirstLaunchCompleted() = store.setFirstLaunchCompleted()

    fun resetFirstLaunch() = store.resetFirstLaunch()

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}