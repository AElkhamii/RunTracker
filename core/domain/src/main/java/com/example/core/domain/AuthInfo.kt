package com.example.core.domain

/** Token Setup **/
data class AuthInfo(
    val accessToken: String,
    val refreshToken: String,
    val userId: String
)
