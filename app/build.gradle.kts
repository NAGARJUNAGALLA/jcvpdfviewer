plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.pdfviewer"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.example.pdfviewer"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.github.mhiew:android-pdf-viewer:3.2.0-beta.3")
}
