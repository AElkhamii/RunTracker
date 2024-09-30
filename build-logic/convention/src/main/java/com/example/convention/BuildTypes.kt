package com.example.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

internal fun Project.configureBuildTypes(
    commonExtension: CommonExtension<*,*,*,*,*,*>,
    extensionType: ExtensionType
){
    commonExtension.run {

        buildFeatures {
            buildConfig = true
        }

        //  Get the API_KEY from Local.properties file
        val apiKey = gradleLocalProperties(projectRootDir = rootDir, providers = providers).getProperty("API_KEY")

        when(extensionType){
            ExtensionType.APPLICATION -> {
                extensions.configure<ApplicationExtension>{
                    buildTypes{
                        debug {
                            configureDebugBuildType(apiKey)
                        }
                        release {
                            configureReleaseBuildType(commonExtension,apiKey)
                        }
                    }
                }
            }
            ExtensionType.LIBRARY -> {
                extensions.configure<LibraryExtension>{
                    buildTypes{
                        debug {
                            configureDebugBuildType(apiKey)
                        }
                        release {
                            configureReleaseBuildType(commonExtension,apiKey)
                        }
                    }
                }
            }
        }
    }
}

/*
 * buildConfigField() configuration are filed that are constants that are compiled into your build during compile time
 * Those constant commonly like API Key and Base URL
 * since we don't want include this api key in our github repository, so we do not want to put that in our actual code,
 * but rather in file that we do not submit to github and load that during compile time and bundle it into our APK.
 * this API Key will be saved in local.properties file, because this file does not uploaded to the github. because this file is included in .gitignore file
 */
private fun BuildType.configureDebugBuildType(apiKey: String){
    buildConfigField("String","API_KEY","\"$apiKey\"")
    // Append port 8080 to the URL because this is just educational version of this backend which is why it shares its base and storage with other backend.
    // so we need complexity define the port here
    buildConfigField("String","BASE_URL","\"https://runique.pl-coding.com:8080\"")
}

private fun BuildType.configureReleaseBuildType(
    commonExtension: CommonExtension<*,*,*,*,*,*>,
    apiKey: String
){
    buildConfigField("String","API_KEY","\"$apiKey\"")
    buildConfigField("String","BASE_URL","\"https://runique.pl-coding.com:8080\"")

    // aide is enabled, it is just a tool that will shrink your APK by removing unnecessary resources and it will also make your code unreadable (obfuscation the code).
    // So it is much harder to reverse engineer the code.
    isMinifyEnabled = true
    // proguard files are just files that are part of every single specific module, such ass app module proguard-rules.pro.
    // which define the specific rules how is this obfuscation should happen which will make your code unreadable.
    // It also shrink your APK by removing unnecessary resources.
    // proguard-rules.pro file makes you to exclude some classes from being obfuscated since sometimes obfuscated could result bugs in your code
    proguardFiles(
        commonExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
    )
}