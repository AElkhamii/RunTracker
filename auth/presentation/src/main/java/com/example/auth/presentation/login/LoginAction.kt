package com.example.auth.presentation.login

sealed interface LoginAction {

    data object OnTogglePasswordVisibility: LoginAction
    data object LoginInClick: LoginAction
    data object OnRegisterClick: LoginAction
}