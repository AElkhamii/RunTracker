plugins {
    alias(libs.plugins.runtracker.android.library)
    alias(libs.plugins.runtracker.android.room)
}

android {
    namespace = "com.example.core.database"
}

dependencies {

    // library used to Generate mongodb ids since the backend of this app work with mongodb
    // we need to generate such id client side that work with the backend data base
    implementation(libs.org.mongodb.bson)

    implementation(projects.core.domain)

//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.appcompat)
//    implementation(libs.material)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
}