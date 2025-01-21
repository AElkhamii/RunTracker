package com.example.runtracker

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.auth.data.AuthRepositoryImpl
import com.example.auth.presentation.intro.IntroScreenRoot
import com.example.auth.presentation.login.LoginScreenRoot
import com.example.auth.presentation.login.LoginViewModel
import com.example.auth.presentation.register.RegisterScreenRoot
import io.ktor.client.HttpClient

//recommendation of creating one navigation graph per feature

@Composable
fun NavigationRoot(
    navController: NavHostController,
    isLoggedIn: Boolean
) {
    NavHost(
        navController = navController,
        /** Change start destination of our graph depend on isLoggedIn state **/
        startDestination = if(isLoggedIn) "run" else "auth"
    ) {
        authGraph(navController)
        runGraph(navController)
    }
}


private fun NavGraphBuilder.authGraph(navController: NavHostController){
    navigation(
        startDestination = "intro",
        route = "auth"
    ){
        composable(route = "intro"){
            IntroScreenRoot(
                onSignUpClick = {
                    navController.navigate("register")
                },
                onSignInClick = {
                    navController.navigate("login")
                }
            )
        }
        composable(route = "register") {
            RegisterScreenRoot(
                onSignInClick = {
                    navController.navigate("login"){
                        // When ever you switch between login and register screen, you will not create new backstack each time you switch between them
                        popUpTo("register"){//pop the back stack up tp
                            inclusive = true
                            saveState = true // means save one instance from this screen to come back to
                        }
                        restoreState = true // to restore the last state that the screen has been on
                    }
                },
                onSuccessfulRegistration = {
                    navController.navigate("login")
                }
            )
        }
        composable(route = "login"){
            LoginScreenRoot(
                onLoginSuccess = {
                    navController.navigate("run"){
                        // to Not return to login screen directly but to auth screen
                        popUpTo("auth") {
                            inclusive = true
                        }
                    }
                },
                onSignUp = {
                    navController.navigate("register"){
                        popUpTo("login"){
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                }
            )
        }
    }
}


private fun NavGraphBuilder.runGraph(navController: NavHostController){
    navigation(
        startDestination = "run_overview",
        route = "run"
    ){
        composable("run_overview"){
            Text(text = "Run Overview")
        }
    }
}