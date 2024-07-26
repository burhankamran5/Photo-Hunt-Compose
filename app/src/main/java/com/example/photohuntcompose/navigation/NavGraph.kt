package com.example.photohuntcompose.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.photohuntcompose.ui.screens.home.HomeScreen
import com.example.photohuntcompose.ui.screens.home.HuntFinishScreen
import com.example.photohuntcompose.ui.screens.huntview.HuntErrorScreen
import com.example.photohuntcompose.ui.screens.huntview.HuntLoadingScreen
import com.example.photohuntcompose.ui.screens.huntview.PhotoHuntLoadedScreen
import com.example.photohuntcompose.ui.screens.itemhunt.HuntItemPendingScreen
import com.example.photohuntcompose.ui.screens.itemhunt.ItemValidatingScreen
import com.example.photohuntcompose.ui.screens.itemhunt.ItemValidationFailureScreen
import com.example.photohuntcompose.ui.screens.itemhunt.ItemValidationSuccessScreen
import com.example.photohuntcompose.viewmodel.HuntViewModel
import com.example.photohuntcompose.viewmodel.PredictionViewModel


@Composable
fun NavGraph(huntViewModel: HuntViewModel, predictionViewModel: PredictionViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) { HomeScreen(navController, huntViewModel) }
        composable(Screen.Loading.route) {
            HuntLoadingScreen(
                navController = navController,
                huntViewModel = huntViewModel
            )
        }
        composable(Screen.Loaded.route) {
            PhotoHuntLoadedScreen(
                navController = navController,
                huntViewModel = huntViewModel
            )
        }
        composable(Screen.Pending.route) {
            HuntItemPendingScreen(
                navController = navController,
                huntViewModel = huntViewModel,
                predictionViewModel = predictionViewModel
            )
        }
        composable(Screen.Finish.route) {
            HuntFinishScreen(
                navController = navController,
                huntViewModel
            )
        }
        composable(Screen.ItemValidating.route) {
            ItemValidatingScreen(
                navController = navController,
                huntViewModel = huntViewModel,
                predictionViewModel = predictionViewModel
            )
        }
        composable(Screen.ItemValidationSuccess.route) {
            ItemValidationSuccessScreen(
                navController = navController,
                huntViewModel = huntViewModel,
                predictionViewModel = predictionViewModel
            )
        }
        composable(Screen.ItemValidationFailure.route) {
            ItemValidationFailureScreen(
                navController = navController,
                huntViewModel = huntViewModel,
                predictionViewModel = predictionViewModel
            )
        }
        composable(Screen.HuntError.route) {
            HuntErrorScreen(
                navController = navController,
                huntViewModel = huntViewModel
            )
        }
    }
}


sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Loading : Screen("loading")
    data object Loaded : Screen("loaded")
    data object Pending : Screen("pending")
    data object Finish : Screen("finish")
    data object ItemValidating : Screen("itemValidating")
    data object ItemValidationSuccess : Screen("itemValidationSuccess")
    data object ItemValidationFailure : Screen("itemValidationFailure")
    data object HuntError : Screen("huntError")
}