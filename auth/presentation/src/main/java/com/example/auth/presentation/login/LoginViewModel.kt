@file:Suppress("OPT_IN_USAGE_FUTURE_ERROR")
@file:OptIn(ExperimentalFoundationApi::class)
package com.example.auth.presentation.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.domain.AuthRepository
import com.example.auth.domain.UserDataValidator
import com.example.auth.presentation.R
import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import com.example.core.presentation.ui.UiText
import com.example.core.presentation.ui.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    /** Get the data from domain repository **/
    private val authRepository: AuthRepository,
    private val userDataValidator: UserDataValidator
): ViewModel() {

    /** Login State Declaration **/
    var state by mutableStateOf(LoginState())
        private set

    /** Login Event Declaration **/
    private val eventChannel = Channel<LoginEvent>()
    val event = eventChannel.receiveAsFlow()

    init {
        combine(state.email.textAsFlow(), state.password.textAsFlow()){email, password ->
            state = state.copy(
                canLogIn = userDataValidator.isValidEmail(email.toString()) && password.isNotEmpty()
            )
        }.launchIn(viewModelScope)
    }

    /** Login Action Declaration **/
    fun onAction(action: LoginAction){
        when(action){
            LoginAction.LoginInClick -> login()
            LoginAction.OnTogglePasswordVisibility -> {
                state = state.copy(isPasswordVisible = !state.isPasswordVisible)
            }
            // Since it is pure navigation related action we already handled it in the Login Screen
//            LoginAction.OnRegisterClick -> TODO()
            else -> Unit
        }
    }

    private fun login() {
        viewModelScope.launch {
            state = state.copy(isLoggingIn = true)
            val result = authRepository.login(state.email.toString().trim(),state.password.toString())
            state = state.copy(isLoggingIn = false)

            when(result){
                is Result.Error -> {
                    if(result.error == DataError.Network.UNAUTHORIZED){
                        eventChannel.send(LoginEvent.Error(UiText.StringResource(R.string.error_email_password_incorrect)))
                    }
                    else {
                        eventChannel.send(LoginEvent.Error(result.error.asUiText()))
                    }
                }
                is Result.Success -> {
                    eventChannel.send(LoginEvent.LoginSuccess)
                }
            }
        }
    }


}

