plugins {
    alias(libs.plugins.runtracker.android.library.compose)
}

android {
    namespace = "com.example.core.presentation.designsystem"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
    // if we just use implementation, we can use Material3 dependencies inside of the designsystem module.
    // but not inside of modules that implement the designsystem module
    // if we use api then all modules that depend on that designsystem can also access the dependencies of Material3 library which are included in designsystem module.
    api(libs.androidx.compose.material3)
}