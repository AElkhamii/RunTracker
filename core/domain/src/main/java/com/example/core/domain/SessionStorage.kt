package com.example.core.domain

/** Token Setup **/
// Implementation as usual will be in Core data
interface SessionStorage {
    suspend fun get(): AuthInfo?
    suspend fun set(info: AuthInfo?)
}