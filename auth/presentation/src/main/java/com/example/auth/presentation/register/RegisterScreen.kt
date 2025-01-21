@file:OptIn(ExperimentalFoundationApi::class, ExperimentalFoundationApi::class,
    ExperimentalFoundationApi::class
)

package com.example.auth.presentation.register

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
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
import com.example.auth.domain.PasswordValidationState
import com.example.auth.domain.UserDataValidator
import com.example.auth.presentation.R
import com.example.core.presentation.designsystem.CheckIcon
import com.example.core.presentation.designsystem.CrossIcon
import com.example.core.presentation.designsystem.EmailIcon
import com.example.core.presentation.designsystem.Poppins
import com.example.core.presentation.designsystem.RunTrackerTheme
import com.example.core.presentation.designsystem.RuniqueDarkRed
import com.example.core.presentation.designsystem.RuniqueGray
import com.example.core.presentation.designsystem.RuniqueGreen
import com.example.core.presentation.designsystem.components.GradiantBackground
import com.example.core.presentation.designsystem.components.RunTrackerActionButton
import com.example.core.presentation.designsystem.components.RunTrackerPasswordTextField
import com.example.core.presentation.designsystem.components.RunTrackerTextField
import com.example.core.presentation.ui.observeAsEvent
import org.koin.androidx.compose.koinViewModel

@Composable
fun RegisterScreenRoot(
    viewModel: RegisterViewModel = koinViewModel(),
    /* these lambda function for propagate navigation call to parent composable to react in root nav graph
     * instead of using navController: NavController */
    onSignInClick: () -> Unit,
    onSuccessfulRegistration: () -> Unit
){
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    observeAsEvent(flow = viewModel.events) {event -> // we got a reference for each single event
        when (event){
            is RegisterEvent.Error -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_LONG
                ).show()
            }
            RegisterEvent.RegistrationSuccess -> {
                keyboardController?.hide()
                Toast.makeText(
                    context,
                    R.string.registration_successful,
                    Toast.LENGTH_LONG
                ).show()
                onSuccessfulRegistration()
            }
        }
    }

    RegisterScreen(
        state = viewModel.state,
//        onAction = viewModel::onAction
        onAction = {action ->
            when(action){
                RegisterAction.OnLoginClick -> onSignInClick()
                else ->  Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
){
    GradiantBackground() {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(vertical = 32.dp)
                .padding(top = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.create_account),
                style = MaterialTheme.typography.headlineMedium
            )

            /* To create string with different styles /  we here just declared the string and we are going to use it in text filed */
            val annotatedString = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = Poppins,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                ){
                    append(stringResource(id = R.string.already_have_an_account) + " ")
                    pushStringAnnotation( //every thing after this will be clickable
                        tag = "clickable_text",
                        annotation = stringResource(id = R.string.login)
                    )
                    withStyle(
                        style = SpanStyle(
                            fontFamily = Poppins,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    ){
                        append(stringResource(id = R.string.login))
                    }
                }
            }
            ClickableText(
                text = annotatedString,
                onClick = { offset ->
                    val annotations = annotatedString.getStringAnnotations(
                        tag = "clickable_text", // Your annotation tag
                        start = offset,
                        end = offset
                    )
                    annotations.firstOrNull()?.let {
                        onAction(RegisterAction.OnLoginClick)
                    }
                }
            )


            Spacer(modifier = Modifier.height(48.dp))
            RunTrackerTextField(
                state = state.email,
                startIcon = EmailIcon,
                endIcon = if(state.isEmailValid) CheckIcon else null,
                hint = stringResource(id = R.string.example_email),
                title = stringResource(id = R.string.email),
                modifier = Modifier.fillMaxWidth(),
                additionalInfo = stringResource(id = R.string.must_be_valid_email),
                keyboardType = KeyboardType.Email
            )

            Spacer(modifier = Modifier.height(16.dp))
            RunTrackerPasswordTextField(
                state = state.password,
                isPasswordVisible = state.isPasswordIsVisible,
                onTogglePasswordVisibility = {onAction(RegisterAction.OnTogglePasswordVisibilityClick)},
                hint = stringResource(id = R.string.password),
                title = stringResource(id = R.string.password),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
            // UserDataValidator used to save constant that will be set inside the string by use place holder like C language %d in string.
            PasswordRequirement(text = stringResource(id = R.string.at_least_x_characters, UserDataValidator.MIN_PASSWORD_LENGTH), isValid = state.passwordValidationState.hasMinLength)
            Spacer(modifier = Modifier.height(4.dp))
            PasswordRequirement(text = stringResource(id = R.string.at_least_one_number), isValid = state.passwordValidationState.hasNumber)
            Spacer(modifier = Modifier.height(4.dp))
            PasswordRequirement(text = stringResource(id = R.string.contains_lowercase_character), isValid = state.passwordValidationState.hasLowerCaseCharacter)
            Spacer(modifier = Modifier.height(4.dp))
            PasswordRequirement(text = stringResource(id = R.string.contains_uppercase_character), isValid = state.passwordValidationState.hasUpperCaseCharacter)

            Spacer(modifier = Modifier.height(32.dp))
            RunTrackerActionButton(
                text = stringResource(id = R.string.register),
                isLoading = state.isRegistering,
                enabled = state.canRegister,
                modifier = Modifier.fillMaxWidth(),
                onClick = {onAction(RegisterAction.OnRegisterClick)}
            )
        }
    }
}

@Composable
fun PasswordRequirement(
    modifier: Modifier = Modifier,
    text: String,
    isValid: Boolean
) {
    Row (
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            imageVector = if(isValid) CheckIcon else CrossIcon,
            contentDescription = null,
            tint = if(isValid) RuniqueGreen else RuniqueDarkRed
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun RegisterScreenPreview(){
    RunTrackerTheme {
        RegisterScreen(
            state = RegisterState(
                passwordValidationState = PasswordValidationState(
//                    hasNumber = true,
//                    hasLowerCaseCharacter = true,
//                    hasUpperCaseCharacter = true,
//                    hasMinLength = true
                )
            ),
            onAction = {}
        )
    }
}


