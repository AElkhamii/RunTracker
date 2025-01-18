package com.example.auth.presentation.register

import com.example.core.presentation.ui.UiText

/** Event for sending information from Viewmodel to UI **/
sealed interface RegisterEvent{
    data object RegistrationSuccess: RegisterEvent
    data class Error(val error: UiText): RegisterEvent
}

