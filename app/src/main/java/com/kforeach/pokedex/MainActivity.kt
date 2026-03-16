package com.kforeach.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.kforeach.pokedex.navigation.Screen
import com.kforeach.pokedex.navigation.setup
import com.kforeach.pokedex.ui.theme.PokeDexTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.Black.toArgb()),
            navigationBarStyle = SystemBarStyle.dark(Color.Black.toArgb())
        )

        setContent {
            val navHostController = rememberNavController()

            PokeDexTheme(darkTheme = false) {
                NavHost(
                    navController = navHostController,
                    startDestination = Screen.MAIN.route,
                    builder = { setup(navHostController = navHostController) }
                )
            }
        }
    }
}
