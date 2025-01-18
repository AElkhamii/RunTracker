@file:Suppress("OPT_IN_USAGE_FUTURE_ERROR")
@file:OptIn(ExperimentalFoundationApi::class)

package com.example.auth.presentation.register

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userDataValidator: UserDataValidator,
    private val repository: AuthRepository
): ViewModel() {
    /** State to change Ui (recompose) when ever one of those variable changes **/
    var state by mutableStateOf(RegisterState())
        private set

    /** Event for sending information from Viewmodel to UI **/
    // you need to go to screen to listen to these specific events
    private val eventChannel = Channel<RegisterEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        /** textAsFlow here gives us a flow of text field character sequence which it is a flow that get trigger whenever we change the text **/
        state.email.textAsFlow()
            .onEach { email -> // on each email change do next
                val isValidEmail = userDataValidator.isValidEmail(email.toString())
                state = state.copy(
                    isEmailValid = isValidEmail,
                    canRegister = isValidEmail && state.passwordValidationState.isValidPassword && !state.isRegistering
                )
            }
            .launchIn(viewModelScope)

        /* Same for the password */
        state.password.textAsFlow()
            .onEach { password ->
                val passwordValidationState = userDataValidator.isValidPassword(password.toString())
                state = state.copy(
                    passwordValidationState = passwordValidationState,
                    canRegister = state.isEmailValid && passwordValidationState.isValidPassword && !state.isRegistering
                )

            }
            .launchIn(viewModelScope)
    }

    /** Action for sending information from UI to Viewmodel **/
    fun onAction(action: RegisterAction){
        when(action){
            // just navigate to the UI screen
//            RegisterAction.OnLoginClick -> TODO()
            RegisterAction.OnRegisterClick -> register()
            RegisterAction.OnTogglePasswordVisibilityClick -> {
                state = state.copy(
                    isPasswordIsVisible = !state.isPasswordIsVisible
                )
            }
            else -> Unit
        }
    }

    private fun register(){
        viewModelScope.launch {
            state = state.copy(isRegistering = true)
            val result = repository.register(state.email.text.toString().trim(), state.password.text.toString().trim())

            state = state.copy(isRegistering = false)


            when(result){
                is Result.Error -> {
                    if(result.error == DataError.Network.CONFLICT){
                        eventChannel.send(RegisterEvent.Error(UiText.StringResource(R.string.error_email_exists)))
                    }
                    else{
                        eventChannel.send(RegisterEvent.Error(result.error.asUiText()))

                    }
                }
                is Result.Success -> {
                    eventChannel.send(RegisterEvent.RegistrationSuccess)
                }
            }
        }
    }
}