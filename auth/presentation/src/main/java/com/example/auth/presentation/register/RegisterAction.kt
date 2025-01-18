package com.example.auth.presentation.register

/** Action for sending information from UI to Viewmodel **/
sealed interface RegisterAction{
    data object OnTogglePasswordVisibilityClick: RegisterAction
    data object OnLoginClick: RegisterAction
    data object  OnRegisterClick: RegisterAction
}

