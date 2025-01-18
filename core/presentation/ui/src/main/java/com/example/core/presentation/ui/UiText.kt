package com.example.core.presentation.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

/* The main purpose of this interface is to determine if the string you are going to use will be saved in the string.xml or not */
sealed interface UiText {
    /* String that we do not want to localize (we do not want to save his string)*/
    data class DynamicString(val value: String): UiText

    /* Each String that we save in our app will contain an Id */
    class StringResource(
        @StringRes val id: Int,
        val args: Array<Any> = arrayOf()
    ): UiText

    @Composable
    fun asString(): String {
        return when (this){
            is DynamicString -> value
            is StringResource -> stringResource(id = id, *args)
        }
    }

    fun asString(context: Context): String {
        return when (this){
            is DynamicString -> value
            is StringResource -> context.getString(id, *args)
        }
    }
}