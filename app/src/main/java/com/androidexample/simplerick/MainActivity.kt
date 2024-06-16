package com.androidexample.simplerick

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.androidexample.network.KtorClient
import com.androidexample.simplerick.screens.CharacterDetailsScreen
import com.androidexample.simplerick.screens.CharacterEpisodeScreen
import com.androidexample.simplerick.screens.HomeScreen
import com.androidexample.simplerick.ui.theme.RickPrimary
import com.androidexample.simplerick.ui.theme.SimpleRickTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var ktorClient: KtorClient

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            SimpleRickTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = RickPrimary
                ) {
                    NavHost(navController = navController, startDestination = "home_screen") {
                        composable("home_screen") {
                            HomeScreen(onCharacterSelected = {
                                navController.navigate("character_details/$it")
                            })
                        }
                        composable(
                            "character_details/{characterId}",
                            arguments = listOf(navArgument("characterId") {
                                type = NavType.IntType
                            })
                        ) {
                            val characterId: Int = it.arguments?.getInt("characterId") ?: 0
                            CharacterDetailsScreen(characterId) {episodeId ->
                                navController.navigate("character_episodes/$episodeId")
                            }
                        }
                        composable(
                            route = "character_episodes/{characterId}",
                            arguments = listOf(navArgument("characterId") {
                                type = NavType.IntType
                            })
                        ) {
                            val characterId: Int = it.arguments?.getInt("characterId") ?: 0
                            CharacterEpisodeScreen(characterId = characterId, ktorClient)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SimpleRickTheme {
        Greeting("Android")
    }
}