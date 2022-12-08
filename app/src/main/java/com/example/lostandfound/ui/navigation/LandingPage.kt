package com.example.lostandfound.ui.navigation


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.lostandfound.ui.viewmodels.LandingPageViewModel

@Composable
fun LandingPage() {

    val navController = rememberNavController()
    val landingPageViewModel: LandingPageViewModel = viewModel()
    val initialRoute = landingPageViewModel.getAuthRoute()

    Scaffold(
        bottomBar = {
            BottomNavBar(navController)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            NavHost(
                navController = navController,
                startDestination = initialRoute,
                route = GraphRoutes.Root
            ) {
                unAuthNavGraph(navController)
                authNavGraph(navController)
            }
        }
    }
}
