plugins {
    alias(libs.plugins.runtracker.android.feature.ui)
}

android {
    namespace = "com.example.auth.presentation"
}

dependencies {

    implementation(projects.core.domain)
    implementation(projects.auth.domain)
    implementation(libs.androidx.monitor)
}