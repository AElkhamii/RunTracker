package com.example.core.presentation.ui

import com.example.core.domain.util.DataError

fun DataError.asUiText(): UiText{
    return when(this){
        DataError.Local.DISK_FULL -> UiText.StringResource(
            R.string.error_disk_full
        )
        DataError.Network.REQUEST_TIMEOUT -> UiText.StringResource(
            R.string.error_request_timeout
        )
        /* We will not handel this here because unauthorized can mean multiple things */
//        DataError.Network.UNAUTHORIZED -> UiText.StringResource(
//            R.string.error_unauthorized
//        )
        /* Same as UNAUTHORIZED */
//        DataError.Network.CONFLICT -> UiText.StringResource(
//            R.string.error_disk_full
//        )
        DataError.Network.TOO_MANY_REQUESTS -> UiText.StringResource(
            R.string.error_many_request
        )
        DataError.Network.NO_INTERNET -> UiText.StringResource(
            R.string.error_no_internet
        )
        DataError.Network.PAYLOAD_TOO_LARGE -> UiText.StringResource(
            R.string.error_payload_too_large
        )
        DataError.Network.SERVER_ERROR -> UiText.StringResource(
            R.string.error_server_error
        )
        DataError.Network.SERIALIZATION -> UiText.StringResource(
            R.string.error_serialization
        )
        /* Remove DataError.unknown with else to remove the error that has been cause by
         * removing DataError.Network.UNAUTHORIZED and DataError.Network.CONFLICT elements from when condition*/
        else -> UiText.StringResource(
            R.string.error_unknown
        )
    }
}