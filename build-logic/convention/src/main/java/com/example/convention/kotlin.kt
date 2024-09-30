package com.example.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Internal means this function or class can only be used in this module
internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*,*,*,*,*,*>
){
    commonExtension.apply {
        compileSdk = libs.findVersion("projectCompileSdkVersion").get().toString().toInt()


        defaultConfig.minSdk = libs.findVersion("projectMinSdkVersion").get().toString().toInt()

        compileOptions{
            isCoreLibraryDesugaringEnabled = true
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
    }

    configureKotlin()

    dependencies {
        // it is just a mechanism that will make java apis compatible to to android api 21.
        // Because there are java apis work only with android api 26+, if we use this library we will make java apis compatible to to android api 21+.
        "coreLibraryDesugaring"(libs.findLibrary("desugar.jdk.libs").get())
    }
}

// function for domain kotlin library domain gradle layer
internal fun Project.configureKotlinJvm(){
    extensions.configure<JavaPluginExtension>{
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    configureKotlin()
}

// This function for every code module that use kotlin
private fun Project.configureKotlin(){
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_11.toString()
        }
    }
}