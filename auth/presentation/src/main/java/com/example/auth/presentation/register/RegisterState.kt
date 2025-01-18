@file:OptIn(ExperimentalFoundationApi::class)

package com.example.auth.presentation.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import com.example.auth.domain.PasswordValidationState

data class RegisterState (
    val email: TextFieldState = TextFieldState(),
    val isEmailValid: Boolean = false,
    val password: TextFieldState = TextFieldState(),
    val isPasswordIsVisible: Boolean = false,

    /* For the remaining state such as password validation state, we will create in auth domain module another data class to validate these validations */
    val passwordValidationState: PasswordValidationState = PasswordValidationState(),
    val isRegistering: Boolean = false,
    val canRegister: Boolean = false
)