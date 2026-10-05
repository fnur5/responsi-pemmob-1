package com.example.watchventure.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.watchventure.ui.screens.DetailScreen
import com.example.watchventure.ui.screens.HomeScreen

object Routes {
    const val HOME = "home"
    const val DETAIL = "detail/{showId}"
    fun detail(id: Int) = "detail/$id"
}

/** Maksimal 2 screen: Home dan Detail. */
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            HomeScreen(onShowClick = { id -> navController.navigate(Routes.detail(id)) })
        }
        composable(
            route = Routes.DETAIL,
            arguments = listOf(navArgument("showId") { type = NavType.IntType })
        ) {
            // DetailViewModel membaca "showId" dari SavedStateHandle
            DetailScreen(onBack = { navController.popBackStack() })
        }
    }
}
