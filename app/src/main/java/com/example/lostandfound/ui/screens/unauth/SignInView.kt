package com.example.lostandfound.ui.screens.unauth

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.lostandfound.ui.components.LAFFormField
import com.example.lostandfound.ui.components.LAFLoadingButton
import com.example.lostandfound.ui.navigation.GraphRoutes
import com.example.lostandfound.ui.navigation.Routes
import com.example.lostandfound.ui.viewmodels.SignInViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignInView(navController: NavController) {

    val viewModel: SignInViewModel = viewModel()
    val state = viewModel.state
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val scope = rememberCoroutineScope()

    LaunchedEffect(state.loginSuccess, state.errorMessage) {
        if (state.loginSuccess) {
            navController.navigate(GraphRoutes.Auth) {
                popUpTo(0)
            }
        }
        if (state.errorMessage != "") {
            Toast.makeText(context, state.errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .clip(RoundedCornerShape(25.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text(text = "Sign In", style = MaterialTheme.typography.h4)
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LAFFormField(
                value = state.email,
                onValueChange = { state.email = it },
                label = "Email",
            )
            LAFFormField(
                value = state.password,
                onValueChange = { state.password = it },
                error = state.signInError,
                label = "Password",
                password = true
            )
        }
        LAFLoadingButton(
            onClick = {
                keyboardController?.hide()
                scope.launch { viewModel.signIn() }
            },
            text = "Sign In",
            loading = state.loading
        )
        Text(
            text = "Create an Account?",
            color = Color.Blue,
            modifier = Modifier
                .clickable {
                    navController.navigate(Routes.CreateAccount)
                },
            style = MaterialTheme.typography.caption
        )

    }
}