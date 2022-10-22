package com.example.lostandfound.ui.navigation


import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.lostandfound.ui.screens.*
import com.example.lostandfound.ui.screens.report.EditLostReport
import com.example.lostandfound.ui.screens.report.LostReportView
import com.example.lostandfound.ui.screens.unauth.CreateAccountView
import com.example.lostandfound.ui.screens.unauth.SignInView


fun NavGraphBuilder.unAuthNavGraph(navController: NavHostController) {
    navigation(
        route = GraphRoutes.Unauth,
        startDestination = Routes.SignIn
    ) {
        composable(Routes.SignIn) { SignInView(navController) }
        composable(Routes.CreateAccount) { CreateAccountView(navController) }
    }
}

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = GraphRoutes.Auth,
        startDestination = Routes.Home
    ) {
        composable(Routes.Home) { HomeView(navController) }
        composable(Routes.MyReports) { MyReports(navController) }
        composable(Routes.Profile) { ProfileView(navController) }
        composable(Routes.CreateLostReport) { CreateReportView(navController) }
        composable(Routes.Conversations) { Conversations(navController) }
        composable(Routes.LostReportView) {
            LostReportView(
                it.arguments?.getString("reportId") ?: "",
                navController
            )
        }

        composable(Routes.LostReportEdit) {
            EditLostReport(
                it.arguments?.getString("reportId") ?: "",
                navController
            )
        }
    }
}
