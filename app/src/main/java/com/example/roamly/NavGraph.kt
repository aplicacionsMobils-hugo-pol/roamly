package com.example.roamly

import ConfigureProfileScreen
import SettingsScreen
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.roamly.ui.view.*

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("profile") { ProfileScreen(navController) }

        composable("profile/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            val userId = it.arguments?.getInt("id") ?: -1
            ProfileScreen(navController)
        }

        composable("profileMenu") { ProfileScreen(navController) }
        composable("about") { AboutScreen(navController) }
        composable("termsAndConditions") { TermsAndConditionsScreen(navController) }
        composable("settings") { SettingsScreen(navController) }
        composable("configureProfile") { ConfigureProfileScreen(navController) }
        composable("register") { RegisterScreen(navController) }

        composable("itinerary/{tripId}",
            arguments = listOf(navArgument("tripId") { type = NavType.StringType })
        ) { backStackEntry ->
            // Obtención del parámetro tripId desde los argumentos
            val tripIdString = backStackEntry.arguments?.getString("tripId")
            val tripId = tripIdString?.toIntOrNull() ?: -1
            //val tripId = 11;

            Log.d("Navigation", "TripId: $tripId")

            // Recuperar el tripName desde savedStateHandle
            val tripName = backStackEntry.savedStateHandle?.get<String>("tripName") ?: "Trip"

            // Llamada a la pantalla ItineraryScreen
            ItineraryScreen(navController, tripId, tripName)
        }
    }
}
