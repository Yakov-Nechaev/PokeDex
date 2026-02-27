package com.kforeach.pokedex.vm

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.kforeach.pokedex.data.Repository
import com.kforeach.pokedex.models.Pokemon
import com.kforeach.pokedex.models.PokemonShort
import kotlinx.coroutines.launch

class MainViewModel(val repository: Repository) : ViewModel() {

    val listPok = mutableStateListOf<PokemonShort>()
    val detailPok = mutableStateOf<Pokemon?>(null)
    var counterTotal = 0
    val primaryCodColor = mutableStateOf<Int>(Color.White.toArgb())

    val stateScreen = mutableStateOf(StateScreen.NaN)
    val message = mutableStateOf("")

    private var isSearchMode = false

    val showInfoDialog = mutableStateOf(false)

    fun clearMessage() {
        message.value = ""
        stateScreen.value = StateScreen.NaN
    }

    init {
//         repository.resetFirstLaunch() // Для сброса раскомментируйте
        checkFirstLaunch()
        getPokDexWithPagination()
    }

    private fun checkFirstLaunch() {
        if (repository.isFirstLaunch()) {
            showInfoDialog.value = true
            repository.setFirstLaunchCompleted()
        }
    }

    fun getPokDexWithPagination() {
        val isFull = counterTotal > 0 && listPok.size >= counterTotal
        if (isSearchMode || stateScreen.value != StateScreen.NaN || isFull) return

        viewModelScope.launch {
            stateScreen.value = StateScreen.Getting

            repository.getPokemonList(limit = 20, offset = listPok.size)
                .onSuccess { dex ->
                    counterTotal = dex.count
                    listPok.addAll(dex.results)
                    stateScreen.value = StateScreen.NaN
                }
                .onError { error ->
                    message.value = error?.message.toString()
                    stateScreen.value = StateScreen.Error
                }
                .onFinish {
                    if (stateScreen.value == StateScreen.Getting) {
                        stateScreen.value = StateScreen.NaN
                    }
                }
        }
    }

    fun searchPokemon(query: String) {
        if (query.isBlank()) return

        viewModelScope.launch {
            stateScreen.value = StateScreen.Getting
            isSearchMode = true

            val limit = if (counterTotal > 0) counterTotal else 2000

            repository.getPokemonList(limit = limit, offset = 0)
                .onSuccess { dex ->
                    val filtered = dex.results.filter {
                        it.name.contains(query, ignoreCase = true)
                    }
                    listPok.clear()
                    listPok.addAll(filtered)
                    stateScreen.value = StateScreen.NaN
                }
                .onError { error ->
                    message.value = error?.message.toString()
                    stateScreen.value = StateScreen.Error
                }
                .onFinish {
                    if (stateScreen.value == StateScreen.Getting) {
                        stateScreen.value = StateScreen.NaN
                    }
                }
        }
    }

    fun resetSearch() {
        if (!isSearchMode) return
        isSearchMode = false
        listPok.clear()
        getPokDexWithPagination()
    }

    fun getPokDetail(idOrName: String) {
        viewModelScope.launch {
            detailPok.value = null
            stateScreen.value = StateScreen.Getting

            repository.getPokemon(idOrName = idOrName)
                .onSuccess { pok ->
                    detailPok.value = pok
                    stateScreen.value = StateScreen.NaN
                }
                .onError { error ->
                    message.value = error?.message.toString()
                    stateScreen.value = StateScreen.Error
                }
                .onFinish {
                    if (stateScreen.value == StateScreen.Getting) {
                        stateScreen.value = StateScreen.NaN
                    }
                }
        }
    }

    fun calcDominantColor(drawable: Drawable?) {
        val bmp = (drawable as? BitmapDrawable)?.bitmap ?: return
        val copy = bmp.copy(Bitmap.Config.ARGB_8888, true)

        Palette.from(copy).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { rgb ->
                primaryCodColor.value = rgb
            }
        }
    }

    fun setPrimeColor(cod: Int) {
        primaryCodColor.value = cod
    }

}

enum class StateScreen { NaN, Getting, Error }
