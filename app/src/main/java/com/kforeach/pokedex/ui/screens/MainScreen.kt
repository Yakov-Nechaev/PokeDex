package com.kforeach.pokedex.ui.screens

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kforeach.pokedex.R
import com.kforeach.pokedex.models.PokemonShort
import com.kforeach.pokedex.navigation.Screen
import com.kforeach.pokedex.ui.items.CustomTextField
import com.kforeach.pokedex.ui.items.DialogInfo
import com.kforeach.pokedex.ui.items.ItemPoc
import com.kforeach.pokedex.ui.theme.PrimaryColor
import com.kforeach.pokedex.vm.MainViewModel
import com.kforeach.pokedex.vm.StateScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainScreen(navigation: NavHostController) {
    val vm: MainViewModel = koinViewModel(
        viewModelStoreOwner = LocalActivity.current as ComponentActivity
    )

    MainScreenLayout(
        shortPocList = vm.listPok,
        stateScreen = vm.stateScreen.value,
        showInfoDialog = vm.showInfoDialog,
        message = vm.message.value,
        onClearMessage = { vm.clearMessage() },
        onShowDialog = { vm.showInfoDialog.value = true },
        onHideDialog = { vm.showInfoDialog.value = false },
        onLoadMore = { vm.getPokDexWithPagination() },
        onSearch = { query -> vm.searchPokemon(query) },
        onReset = { vm.resetSearch() },
        onNavigateDetail = { name, codColor ->
            if (vm.stateScreen.value != StateScreen.Error) {
                vm.getPokDetail(idOrName = name)
                vm.setPrimeColor(cod = codColor)
                navigation.navigate(route = Screen.DETAIL.route)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenLayout(
    shortPocList: List<PokemonShort>,
    stateScreen: StateScreen,
    showInfoDialog: State<Boolean>,
    message: String,
    onClearMessage: () -> Unit,
    onShowDialog: () -> Unit,
    onHideDialog: () -> Unit,
    onLoadMore: () -> Unit,
    onSearch: (String) -> Unit,
    onReset: () -> Unit,
    onNavigateDetail: (name: String, codColor: Int) -> Unit
) {
    val text = remember() { mutableStateOf("") }
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(message) {
        if (message.isNotEmpty()) {
            val result = snackBarHostState.showSnackbar(
                message = message,
                withDismissAction = true,
                duration = SnackbarDuration.Long
            )

            if (result == SnackbarResult.Dismissed || result == SnackbarResult.ActionPerformed) {
                onClearMessage()
            }
        }
    }

    if (showInfoDialog.value) {
        DialogInfo(onDismiss = { onHideDialog() })
    }

    Scaffold(
        containerColor = PrimaryColor,
        modifier = Modifier.windowInsetsPadding(insets = WindowInsets.systemBars),
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                snackbar = {
                    Snackbar(
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .padding(horizontal = 8.dp)
                            .clickable(onClick = {
                                onClearMessage()
                            }),
                        snackbarData = it,
                        containerColor = Color.White,
                        contentColor = PrimaryColor,
                        actionColor = PrimaryColor,
                        dismissActionContentColor = PrimaryColor,
                        shape = RoundedCornerShape(8.dp)
                    )
                }
            )
        },
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Image(
                        painter = painterResource(R.drawable.pokdex),
                        contentDescription = "app logo",
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .size(30.dp)
                    )
                },
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(start = 8.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = "Pokédex",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                            modifier = Modifier.horizontalScroll(rememberScrollState()),
                            fontSize = 28.sp,
                            color = Color.White
                        )
                    }

                },
                actions = {
                    IconButton(onClick = { onLoadMore() }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh list",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = { onShowDialog() }) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Info",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PrimaryColor)
            )
        },
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = PrimaryColor)
                .padding(paddingValues)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 16.dp)
            ) {
                CustomTextField(
                    value = text.value,
                    onValueChange = { text.value = it },
                    placeholder = "Search...",
                    iconEnd = {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear",
                            tint = PrimaryColor,
                            modifier = Modifier.clickable(
                                onClick = {
                                    text.value = ""
                                    onReset()
                                }
                            )
                        )
                    },
                    iconStart = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = PrimaryColor,
                            modifier = Modifier.clickable(
                                onClick = {
                                    onSearch(text.value)
                                }
                            )
                        )
                    },
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 4.dp)
                    .clip(RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp))
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 18.dp, horizontal = 4.dp),
                    contentPadding = PaddingValues(6.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(shortPocList) { index, pokemon ->

                        if (index >= shortPocList.lastIndex - 5) {
                            onLoadMore()
                        }

                        ItemPoc(
                            pokemonShort = pokemon,
                            onSelect = { codColor ->
                                onNavigateDetail(pokemon.name, codColor)
                            }
                        )
                    }
                }

                if (stateScreen == StateScreen.Getting) {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth(0.90f)
                            .height(6.dp),
                        color = PrimaryColor,
                        trackColor = PrimaryColor.copy(alpha = 0.4f)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    val list = List(30) { PokemonShort.previewPokemonShort() }
    MainScreenLayout(
        shortPocList = list,
        stateScreen = StateScreen.NaN,
        showInfoDialog = remember { mutableStateOf(false) },
        message = "",
        onClearMessage = {},
        onShowDialog = {},
        onHideDialog = {},
        onLoadMore = {},
        onSearch = {},
        onReset = {},
        onNavigateDetail = { _, _ -> }
    )
}
