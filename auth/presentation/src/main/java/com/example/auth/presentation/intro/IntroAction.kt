package com.example.auth.presentation.intro

/* you will not need UI state class for this screen */
sealed interface IntroAction {
    data object OnSignInClick: IntroAction
    data object OnSignUpClick: IntroAction
}