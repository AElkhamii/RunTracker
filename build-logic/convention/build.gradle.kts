plugins {
    `kotlin-dsl`
}

group = "com.example.RunTracker.buildlogic"

dependencies{
    // CompileOnly will only include those libraries during compile time not after that
    // because it only apply some gradle config and will not do any thing during runtime.
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

gradlePlugin{
    plugins {
        register("androidApplication"){
            id = "runtracker.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidApplicationCompose"){
            id = "runtracker.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }

        register("androidLibrary"){
            id = "runtracker.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidLibraryCompose"){
            id = "runtracker.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }

        register("androidFeatureUi"){
            id = "runtracker.android.feature.ui"
            implementationClass = "AndroidFeatureUiConventionPlugin"
        }

        register("androidRoom"){
            id = "runtracker.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }

        register("jvmLibrary"){
            id = "runtracker.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }

        register("jvmKtor"){
            id = "runtracker.jvm.ktor"
            implementationClass = "JvmKtorConventionPlugin"
        }
    }
}