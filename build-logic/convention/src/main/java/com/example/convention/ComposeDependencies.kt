package com.example.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.project

fun DependencyHandlerScope.addUiLayerDependency(project: Project){
    // Our core module Ui and Presentation module implementation
    "implementation"(project(":core:presentation:ui"))
    "implementation"(project(":core:presentation:designsystem"))

    /* Compose Ui libraries */
    "implementation"(project.libs.findBundle("compose").get())
    "debugImplementation"(project.libs.findBundle("compose.debug").get())
    "androidTestImplementation"(project.libs.findLibrary("androidx.compose.ui.test.junit4").get())

    /* Koin Libraries */
    "implementation"(project.libs.findBundle("koin.compose").get())
}