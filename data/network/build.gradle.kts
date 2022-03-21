import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    defaultConfig {
        compileSdk = AndroidSdk.targetSdkVersion
        minSdk = AndroidSdk.minSdkVersion
        targetSdk = AndroidSdk.targetSdkVersion

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

}

dependencies {

    implementation(project(Libraries.Modules.common))
    implementation(project(Libraries.Modules.domain))

    implementation(Libraries.Kotlin.stdlib)
    implementation(Libraries.Coroutines.core)

    //Retrofit
    api(Libraries.Retrofit.retrofit)
    api(Libraries.Retrofit.gson)

    //Okhttp
    api(Libraries.OkHttp.logging)

    //Hilt
    implementation(Libraries.Google.Hilt.android)
    kapt(Libraries.Google.Hilt.compiler)

    //Test
    implementation(Libraries.JUnit.junit)
    implementation(Libraries.Google.truth)
    implementation(Libraries.OkHttp.mockWebServer)
}