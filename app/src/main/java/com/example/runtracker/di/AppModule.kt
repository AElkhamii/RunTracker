package com.example.runtracker.di

import com.example.auth.data.EmailPatternValidator
import com.example.auth.domain.PatternValidator
import com.example.auth.domain.UserDataValidator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
/* why koin better in multi-module
 * 1) Because it is completely kotlin code which can be used in KMP
 * 2) Because it is more fixable with dynamic feature module while dagger hilt can not work with it  */

val appModule = module {

}