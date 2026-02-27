package com.kforeach.pokedex.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kforeach.pokedex.models.Pokemon
import com.kforeach.pokedex.ui.items.ItemAbout
import com.kforeach.pokedex.ui.items.ItemImage
import com.kforeach.pokedex.ui.items.StatLineBox
import com.kforeach.pokedex.ui.theme.PrimaryColor
import com.kforeach.pokedex.ui.theme.TypeBug
import com.kforeach.pokedex.ui.theme.TypeDark
import com.kforeach.pokedex.ui.theme.TypeDragon
import com.kforeach.pokedex.ui.theme.TypeElectric
import com.kforeach.pokedex.ui.theme.TypeFairy
import com.kforeach.pokedex.ui.theme.TypeFighting
import com.kforeach.pokedex.ui.theme.TypeFire
import com.kforeach.pokedex.ui.theme.TypeFlying
import com.kforeach.pokedex.ui.theme.TypeGhost
import com.kforeach.pokedex.ui.theme.TypeGrass
import com.kforeach.pokedex.ui.theme.TypeGround
import com.kforeach.pokedex.ui.theme.TypeIce
import com.kforeach.pokedex.ui.theme.TypeNormal
import com.kforeach.pokedex.ui.theme.TypePoison
import com.kforeach.pokedex.ui.theme.TypePsychic
import com.kforeach.pokedex.ui.theme.TypeRock
import com.kforeach.pokedex.ui.theme.TypeSteel
import com.kforeach.pokedex.ui.theme.TypeWater
import com.kforeach.pokedex.vm.MainViewModel
import com.kforeach.pokedex.vm.StateScreen


@Composable
fun DetailScreen(vm: MainViewModel, navigation: NavHostController) {
    DetailScreenLayout(
        pokemon = vm.detailPok.value,
        codColor = vm.primaryCodColor.value,
        message = vm.message.value,
        stateScreen = vm.stateScreen.value,
        onBackNavigate = {
            navigation.popBackStack()
        },
        onClearMessage = {
            vm.clearMessage()
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenLayout(
    pokemon: Pokemon?,
    onBackNavigate: () -> Unit,
    onClearMessage: () -> Unit,
    codColor: Int,
    message: String,
    stateScreen: StateScreen,
) {
    val isInPreview = LocalInspectionMode.current
    val topPadding = 120.dp
    val sizeImage = 160.dp
    val primeColor = Color(codColor)
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(message) {
        if (message.isNotEmpty()) {
            val result = snackBarHostState.showSnackbar(
                message = message,
                withDismissAction = true,
                duration = SnackbarDuration.Long
            )

            if (result == SnackbarResult.Dismissed || result == SnackbarResult.ActionPerformed) {
                onBackNavigate()
                onClearMessage()
            }
        }
    }

    Scaffold(
        containerColor = Color(codColor),
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
                                onBackNavigate()
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
                    IconButton(onClick = { onBackNavigate() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "back",
                            modifier = Modifier
                                .size(30.dp),
                            tint = Color.White
                        )
                    }
                },
                title = {
                    Box(
                        modifier = Modifier.fillMaxHeight(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            text = if (stateScreen == StateScreen.Error) "Error" else pokemon?.name.orEmpty(),
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                            modifier = Modifier.horizontalScroll(rememberScrollState()),
                            fontSize = 28.sp,
                            color = Color.White
                        )
                    }

                },
                actions = {
                    if (stateScreen != StateScreen.Error) {
                        Text(
                            text = pokemon?.id?.let { "#${it.toString().padStart(3, '0')}" } ?: "",
                            modifier = Modifier.padding(horizontal = 16.dp),
                            fontSize = 18.sp,
                            color = Color.White,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = primeColor)
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = primeColor)
                .padding(top = paddingValues.calculateTopPadding()),
            contentAlignment = Alignment.Center
        ) {
            when (stateScreen) {
                StateScreen.Getting -> {
                    CircularProgressIndicator(color = Color.White)
                }

                StateScreen.Error -> {
                    Text(
                        text = "No data to display",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                else -> {
                    Box(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .align(alignment = Alignment.BottomCenter)
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(horizontal = 4.dp)
                                .padding(bottom = 12.dp)
                                .padding(top = topPadding + sizeImage / 2)
                                .clip(RoundedCornerShape(26.dp))
                                .background(Color.White)
                                .navigationBarsPadding(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {

                            Spacer(modifier = Modifier.height(16.dp + sizeImage / 2))

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                pokemon?.types?.forEach { typeName ->
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(50))
                                            .background(color = getPokemonTypeColor(typeName))
                                            .padding(horizontal = 12.dp, vertical = 6.dp)
                                    ) {
                                        Text(
                                            text = typeName,
                                            fontSize = 16.sp,
                                            color = Color.White,
                                            style = MaterialTheme.typography.labelLarge.copy(
                                                fontWeight = FontWeight.SemiBold
                                            ),
                                        )
                                    }
                                }
                            }


                            Text(
                                text = "About",
                                fontSize = 16.sp,
                                color = primeColor,
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                                modifier = Modifier.padding(top = 16.dp, bottom = 12.dp)
                            )
                            pokemon?.let {
                                ItemAbout(pokemon = pokemon)
                            }


                            Text(
                                text = "Base Stats",
                                fontSize = 16.sp,
                                color = primeColor,
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold),
                                modifier = Modifier.padding(vertical = 12.dp)
                            )

                            pokemon?.let {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth(0.9f)
                                        .padding(bottom = 24.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp),
                                ) {
                                    StatLineBox(lab = "HP", value = pokemon.hp, cod = codColor)
                                    StatLineBox(lab = "ATK", value = pokemon.atk, cod = codColor)
                                    StatLineBox(lab = "DEF", value = pokemon.def, cod = codColor)
                                    StatLineBox(lab = "SATK", value = pokemon.satk, cod = codColor)
                                    StatLineBox(lab = "SDEF", value = pokemon.sdef, cod = codColor)
                                    StatLineBox(lab = "SPD", value = pokemon.spd, cod = codColor)
                                }
                            }
                        }

                        pokemon?.let {
                            ItemImage(
                                pokemon = pokemon,
                                topPadding = topPadding,
                                sizeImage = sizeImage,
                                isInPreview = isInPreview,
                            )
                        }
                    }
                }
            }
        }
    }
}

fun getPokemonTypeColor(type: String): Color {
    return when (type.lowercase()) {
        "normal" -> TypeNormal
        "fire" -> TypeFire
        "water" -> TypeWater
        "electric" -> TypeElectric
        "grass" -> TypeGrass
        "ice" -> TypeIce
        "fighting" -> TypeFighting
        "poison" -> TypePoison
        "ground" -> TypeGround
        "flying" -> TypeFlying
        "psychic" -> TypePsychic
        "bug" -> TypeBug
        "rock" -> TypeRock
        "ghost" -> TypeGhost
        "dragon" -> TypeDragon
        "dark" -> TypeDark
        "steel" -> TypeSteel
        "fairy" -> TypeFairy
        else -> Color.Gray
    }
}


@Preview
@Composable
fun DetailScreenPreview() {
    val previewPokemon = Pokemon.getPreviewPokemon()
    DetailScreenLayout(
        pokemon = previewPokemon,
        codColor = 0xFFF2C94C.toInt(),
        onBackNavigate = {},
        onClearMessage = {},
        message = "",
        stateScreen = StateScreen.NaN,
    )
}
