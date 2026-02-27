package com.kforeach.pokedex.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.kforeach.pokedex.ui.screens.DetailScreen
import com.kforeach.pokedex.ui.screens.MainScreen
import com.kforeach.pokedex.vm.MainViewModel


enum class Screen(val route: String) {
    DETAIL("detail"),
    MAIN("main"),

}

fun NavGraphBuilder.setup(
    navHostController: NavHostController,
    viewModel: MainViewModel
) {
    composable(route = Screen.DETAIL.route) {
        DetailScreen(vm = viewModel, navigation = navHostController)
    }

    composable(route = Screen.MAIN.route) {
        MainScreen(vm = viewModel, navigation = navHostController)
    }
}