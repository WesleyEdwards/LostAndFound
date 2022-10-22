package com.example.lostandfound.ui.screens.unauth

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.lostandfound.ui.components.LAFFormField
import com.example.lostandfound.ui.components.LAFLoadingButton
import com.example.lostandfound.ui.navigation.Routes
import com.example.lostandfound.ui.viewmodels.CreateAccountViewModel
import kotlinx.coroutines.launch

@Composable
fun CreateAccountView(navController: NavController) {

    val viewModel: CreateAccountViewModel = viewModel()
    val state = viewModel.state

    val scope = rememberCoroutineScope()

    LaunchedEffect(state.loginSuccess) {
        if (state.loginSuccess) {
            navController.navigate(Routes.Home) {
                popUpTo(0)
            }
        }
    }

    if (state.errorMessage.isNotEmpty()) {
        AlertDialog(
            onDismissRequest = { state.errorMessage = "" },
            confirmButton = { state.errorMessage = "" },
            title = { Text(text = "Error") },
            text = { Text(text = state.errorMessage) },
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(text = "Create Account", style = MaterialTheme.typography.h4)
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LAFFormField(
                value = state.name,
                label = "Name",
                placeholder = "Mary Jane",
                onValueChange = { state.name = it },
            )
            LAFFormField(
                value = state.email,
                onValueChange = { state.email = it },
                label = "Email",
                placeholder = "maryjane@gmail.com",
                error = state.emailError
            )
            LAFFormField(
                value = state.password,
                onValueChange = { state.password = it },
                label = "Password",
                error = state.passwordError,
                password = true,
            )
            LAFFormField(
                value = state.confirmPassword,
                onValueChange = { state.confirmPassword = it },
                label = "Password Confirmation",
                error = state.confirmPasswordError,
                password = true
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        LAFLoadingButton(
            onClick = { scope.launch { viewModel.createAccount() } },
            text = "Create Account",
            loading = state.loading
        )
    }
}