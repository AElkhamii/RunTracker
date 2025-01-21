@file:OptIn(ExperimentalFoundationApi::class)

package com.example.auth.presentation.login

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.auth.presentation.R
import com.example.core.presentation.designsystem.EmailIcon
import com.example.core.presentation.designsystem.Poppins
import com.example.core.presentation.designsystem.RunTrackerTheme
import com.example.core.presentation.designsystem.components.GradiantBackground
import com.example.core.presentation.designsystem.components.RunTrackerActionButton
import com.example.core.presentation.designsystem.components.RunTrackerPasswordTextField
import com.example.core.presentation.designsystem.components.RunTrackerTextField
import com.example.core.presentation.ui.observeAsEvent
import org.koin.androidx.compose.koinViewModel

@Composable

fun LoginScreenRoot(

    viewModel: LoginViewModel = koinViewModel(),

    onLoginSuccess: () -> Unit,
    onSignUp:() -> Unit

) {
    val context = LocalContext.current
    val keyBoardController = LocalSoftwareKeyboardController.current
    observeAsEvent(flow = viewModel.event) {event ->
        when(event){
            is LoginEvent.Error -> {
                keyBoardController?.hide()
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
            LoginEvent.LoginSuccess -> {
                keyBoardController?.hide()
                Toast.makeText(
                    context,
                    R.string.your_logged_in,
                    Toast.LENGTH_LONG
                ).show()

                onLoginSuccess()
            }
        }
    }

    LoginScreen(
        state = viewModel.state,

//        onAction = viewModel::onAction
        onAction = {action ->
            when(action){
                LoginAction.OnRegisterClick -> onSignUp()
                else -> Unit
            }
            viewModel.onAction(action)
        }

    )

}

@Composable
private fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit
) {
    GradiantBackground {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(vertical = 32.dp)
                .padding(top = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.hi_there),
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = stringResource(id = R.string.run_tracker_welcome_text),
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(48.dp))
            RunTrackerTextField(
                state = state.email,
                startIcon = EmailIcon,
                endIcon = null,
                keyboardType = KeyboardType.Email,
                hint = stringResource(id = R.string.example_email),
                title = stringResource(id = R.string.email),
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(16.dp))
            RunTrackerPasswordTextField(
                state = state.password,
                isPasswordVisible = state. isPasswordVisible,
                onTogglePasswordVisibility = {
                    onAction(LoginAction.OnTogglePasswordVisibility)
                },
                hint = stringResource(id = R.string.password),
                title = stringResource(id = R.string.password),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))
            RunTrackerActionButton(
                text = stringResource(id = R.string.login),
                isLoading = state.isLoggingIn,
                enabled = state.canLogIn && !state.isLoggingIn, // !!second state to disable the button when you press login to prevent many request from the user
                onClick = {
                    onAction(LoginAction.LoginInClick)
                }
            )




            /* To create string with different styles /  we here just declared the string and we are going to use it in text filed */
            val annotatedString = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = Poppins,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ){
                    append(stringResource(id = R.string.do_not_have_an_account) + " ")
                    pushStringAnnotation( //every thing after this will be clickable
                        tag = "clickable_text",
                        annotation = stringResource(id = R.string.sign_up)
                    )
                    withStyle(
                        style = SpanStyle(
                            fontFamily = Poppins,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    ){
                        append(stringResource(id = R.string.sign_up))
                    }
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .weight(1f),
                contentAlignment = Alignment.BottomCenter
            ) {
                ClickableText(
                    text = annotatedString,
                    onClick = { offset ->
                        annotatedString.getStringAnnotations(
                            tag = "clickable_text",
                            start = offset,
                            end = offset
                        ).firstOrNull()?.let {
                            onAction(LoginAction.OnRegisterClick)
                        }
                    }
                )
            }
        }
    }

}

@Preview
@Composable
private fun LoginScreenPreview() {
    RunTrackerTheme {
        LoginScreen(
            state = LoginState(),
            onAction = {}
        )
    }
}