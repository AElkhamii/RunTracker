package com.example.runtracker.di

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.auth.data.EmailPatternValidator
import com.example.auth.domain.PatternValidator
import com.example.auth.domain.UserDataValidator
import com.example.core.domain.SessionStorage
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
/* why koin better in multi-module
 * 1) Because it is completely kotlin code which can be used in KMP
 * 2) Because it is more fixable with dynamic feature module while dagger hilt can not work with it  */

val appModule = module {
    /** Provide dependencies for shared preferences **/
    single<SharedPreferences> {
        EncryptedSharedPreferences(
            androidApplication(), //to get context in koin
            "auth_pref",
            MasterKey(androidApplication()),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}