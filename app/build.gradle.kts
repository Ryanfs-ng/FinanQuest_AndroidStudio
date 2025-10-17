// Add this plugins block at the top of your app/build.gradle.kts file
plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.finanquest"
    compileSdk = 36 // Make sure this is a valid SDK version

    defaultConfig {
        applicationId = "com.example.finanquest"
        minSdk = 34 // It's recommended to use a lower minSdk for broader compatibility
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.recyclerview)
    implementation(libs.google.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    // You have two recyclerview dependencies, you might only need one.
    // implementation(libs.recyclerview.v132)
    implementation(libs.lifecycle.runtime)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.mpandroidchart)
    // This is likely a duplicate of libs.mpandroidchart. Check your libs.versions.toml
    // implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")
}
