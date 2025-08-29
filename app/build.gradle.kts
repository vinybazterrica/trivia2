import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

val localProperties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}
val apiKey: String = localProperties.getProperty("API_KEY", "")

android {
    namespace = "com.viny.trivia2"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.viny.trivia2"
        minSdk = 24
        targetSdk = 36
        versionCode = 11
        versionName = "1.$versionCode-beta"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "API_KEY", "\"$apiKey\"")
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
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //Material3
    implementation(libs.androidx.material3)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    //Corrutina
    implementation(libs.coroutines.android)

    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:34.1.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-crashlytics-ndk")
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-inappmessaging-display")

    //Lottie
    implementation(libs.lottie)
}