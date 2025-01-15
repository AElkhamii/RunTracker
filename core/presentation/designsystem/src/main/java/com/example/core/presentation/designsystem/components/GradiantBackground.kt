package com.example.core.presentation.designsystem.components

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.example.core.presentation.designsystem.RunTrackerTheme

@Composable
fun GradiantBackground(
    modifier: Modifier = Modifier,
    hasToolBar: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
){
    /** Some calculations to make it look fine with different screen sizes **/
    val density = LocalDensity.current
    val configuration = LocalConfiguration.current

    val screenWidthPx = with(density){
        configuration.screenWidthDp.dp.roundToPx()
    }

    val smallDimension = minOf( //whatever small will be assigned to the small Dimension variable
        configuration.screenWidthDp.dp,
        configuration.screenHeightDp.dp
    )
    val smallDimensionPx = with(density){
        smallDimension.roundToPx()
    }

    // get access to our primary color
    val primaryColor = MaterialTheme.colorScheme.primary

    val itAtLeastAndroid12 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S


    // make black background then make radialGradient from primary color to the background color in another box
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ){
        Box(
            modifier = modifier
                .fillMaxSize()
                .then(
                    if (itAtLeastAndroid12) {
                        Modifier.blur(
                            smallDimension / 4f
                        )
                    } else Modifier
                )
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            if (itAtLeastAndroid12) primaryColor else primaryColor.copy(alpha = 0.3f),
                            MaterialTheme.colorScheme.background
                        ),
                        center = Offset(
                            x = screenWidthPx / 2f,
                            y = -100f
                        ),
                        radius = if (configuration.isScreenRound) {
                            smallDimensionPx * 2f
                        } else {
                            smallDimensionPx / 2f
                        }
                    )
                )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .then(
                    if (hasToolBar){
                        Modifier // we will use scaffold when will be a tool bar
                    }else{
                        Modifier.systemBarsPadding()
                    }
                )
        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun GradiantBackgroundPreview(){
    RunTrackerTheme {
        GradiantBackground(
            modifier = Modifier.fillMaxSize()
        ) {

        }
    }
}