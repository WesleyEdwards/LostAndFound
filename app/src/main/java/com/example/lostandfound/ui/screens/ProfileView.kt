package com.example.lostandfound.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.lostandfound.ui.navigation.GraphRoutes
import com.example.lostandfound.ui.viewmodels.ProfileViewModel
import kotlinx.coroutines.launch

@Composable
fun ProfileView(navController: NavHostController) {

    val viewModel: ProfileViewModel = viewModel()
    val state = viewModel.state

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = state.logoutSuccess) {
        if (state.logoutSuccess) {
            navController.navigate(GraphRoutes.Unauth) {
                popUpTo(GraphRoutes.Unauth)
            }
        }
    }
    LaunchedEffect(true) {
        viewModel.getUser()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .clip(RoundedCornerShape(25.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Profile",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(0.dp, 12.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
        ) {
            Column() {
                Text(
                    modifier = Modifier.padding(4.dp, 8.dp),
                    text = "Name: ${state.user?.displayName ?: "Issue loading User"}",
                    style = MaterialTheme.typography.body1
                )
                Text(
                    modifier = Modifier.padding(4.dp, 8.dp),
                    text = "Email: ${state.user?.email ?: "Issue loading User"}",
                    style = MaterialTheme.typography.body1
                )
            }
        }
        Button(
            modifier = Modifier
                .fillMaxWidth(.8f)
                .padding(0.dp, 48.dp),
            onClick = { scope.launch { viewModel.logout() } }) {
            Text(text = "Log Out")
        }
    }
}