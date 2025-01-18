plugins {
    alias(libs.plugins.runtracker.android.library)
    alias(libs.plugins.runtracker.jvm.ktor)
}

android {
    namespace = "com.example.auth.data"
}

dependencies {

    // Koin
    implementation(libs.bundles.koin)

    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.auth.domain)
}