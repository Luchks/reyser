package com.tuempresa.miapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tuempresa.miapp.ui.screens.*
import com.tuempresa.miapp.viewmodel.MainViewModel

@Composable
fun NavGraph(navController: NavHostController, viewModel: MainViewModel) {
    NavHost(navController = navController, startDestination = "splash") {
        
        composable("splash") { SplashScreen(navController) }
        composable("login") { LoginScreen(navController, viewModel) }
        composable("home") { HomeScreen(navController, viewModel) }
        composable("trash") { TrashScreen(navController, viewModel) }

        // ✅ RUTA ÚNICA: Reemplaza a AddEdit y ItemDetail
        composable(
            route = "reservation/{itemId}",
            arguments = listOf(navArgument("itemId") { type = NavType.IntType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt("itemId") ?: -1
            
            ItemReservationScreen(
                itemId = itemId,
                vm = viewModel,
                navController = navController
            )
        }
    }
}
