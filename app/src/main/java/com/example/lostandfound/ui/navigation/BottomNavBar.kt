package com.example.lostandfound.ui.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.lostandfound.R

@Composable
fun BottomNavBar(navController: NavHostController) {

    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Reports,
        BottomBarScreen.Conversations,
        BottomBarScreen.Profile
    )

    val validDestination =
        navController
            .currentBackStackEntryAsState().value?.destination?.route in screens.map { it.route }

    if (validDestination) {
        BottomNavigation {
            val backStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = backStackEntry?.destination

            screens.forEach { screen ->
                RouteTab(
                    screen = screen,
                    navController = navController,
                    currentRoute = currentRoute
                )
            }
        }
    }
}

@Composable
fun RowScope.RouteTab(
    navController: NavController,
    currentRoute: NavDestination?,
    screen: BottomBarScreen
) {
    BottomNavigationItem(
        selected = currentRoute?.hierarchy?.any { it.route == screen.route }
            ?: false,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        icon = { Icon(painterResource(id = screen.icon), "Description") },
    )
}

sealed class BottomBarScreen(
    val route: String,
    val icon: Int
) {
    object Home : BottomBarScreen(
        route = Routes.Home,
        icon = R.drawable.ic_baseline_home_24
    )

    object Reports : BottomBarScreen(
        route = Routes.LostReports,
        icon = R.drawable.ic_baseline_text_snippet_24
    )
    object Conversations : BottomBarScreen(
        route = Routes.Conversations,
        icon = R.drawable.ic_baseline_message_24
    )

    object Profile : BottomBarScreen(
        route = Routes.Profile,
        icon = R.drawable.ic_baseline_person_24
    )
}