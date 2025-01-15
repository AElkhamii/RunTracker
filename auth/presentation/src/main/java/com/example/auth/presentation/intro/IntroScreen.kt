package com.example.auth.presentation.intro

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.auth.presentation.R
import com.example.core.presentation.designsystem.LogoIcon
import com.example.core.presentation.designsystem.RunTrackerTheme
import com.example.core.presentation.designsystem.components.GradiantBackground
import com.example.core.presentation.designsystem.components.RunTrackerActionButton
import com.example.core.presentation.designsystem.components.RunTrackerOutLinedActionButton

@Composable
/* The mistake that always happen when you give parameters to the screen composable function
 * by doing that you will not be able to preview the screen also it will be very hard to write isolated ui test
 * for specific screen when you need to pass in a specific instance of a state amd make assert (sure) that the UI look as intended */

/* To solve this dependencies problem we will create a separate composable for each separate screen
 * Which will be called Intro screen route, this will be composable that take parameters that we do not want they to be in IntroScreen composable
 * like nave controller and view model. and all that will do is it will simply call this child IntroScreen() which will really only contain state and onAction parameters */

/* Note: In multi module project you usually do not want to pass navController down to specific feature modules like our auth feature here.
 * why so, because we want to keep this feature reusable and we do not want to in code our actual feature to feature navigation inside a feature itself.
 * because that way our feature in directly knows about other features and if we have some login screen here and our log in was successful, and we would say
 * nav controller navigate to run overview screen, it will technically work without depending on the run features, but that way we would not be able to reuse auth feature
 * without reusing the run feature since we hard codded inside the auth feature that after logging in we get to the rn feature but
 * if we want to be able to reuse auth feature this should be flexible, this should tell us log in was successful and depending on the type of app you build
 * this should lead to a different screen.  */
/* So in multi module app we instead want to provide lambdas when we want to perform navigation instead of using navController directly */
fun IntroScreenRoot(
    //navController: xxxxx do not use it like that
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit
){
    IntroScreen (
        onAction = { introAction ->
            when(introAction){
                IntroAction.OnSignInClick -> onSignInClick()
                IntroAction.OnSignUpClick -> onSignUpClick()
            }
        }
    )
}

@Composable
fun IntroScreen(
    onAction: (IntroAction)-> Unit
){
    GradiantBackground {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ){
            RunTrackerLogoVertical()
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(bottom = 48.dp)
        ) {
            Text(
                text = stringResource(id = R.string.welcome_to_run_tracker),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(id = R.string.run_tracker_description),
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(32.dp))
            RunTrackerOutLinedActionButton(
                text = stringResource(id = R.string.sign_in) ,
                isLoading = false,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onAction(IntroAction.OnSignInClick)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))
            RunTrackerActionButton(
                text = stringResource(id = R.string.sign_up) ,
                isLoading = false,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onAction(IntroAction.OnSignUpClick)
                }
            )

        }
    }
}

@Composable
private fun RunTrackerLogoVertical(
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = LogoIcon,
            contentDescription = "Logo",
            tint = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(id = R.string.run_tracker),
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview
@Composable
private fun IntroScreenPreview(){
    RunTrackerTheme {
        IntroScreen(
            onAction = {}
        )
    }
}