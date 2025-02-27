package com.example.navigation.ui.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ProfileScreen(navController: NavController, userId: Int?) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Profile Screen - UserId: $userId")
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            // Navigate back to Home
            navController.navigate("home") {
                popUpTo("home") { inclusive = true }
            }
        }) {
            Text(text = "Back to Home")
        }
    }
}