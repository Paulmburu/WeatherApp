
plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id(Libraries.Jacoco.jacocoAndroid)
}

jacoco {
    toolVersion = Libraries.Jacoco.version
}

android {
    defaultConfig {
        applicationId = AndroidSdk.applicationId

        compileSdk = AndroidSdk.targetSdkVersion
        minSdk = AndroidSdk.minSdkVersion
        targetSdk = AndroidSdk.targetSdkVersion

        versionCode = AndroidSdk.versionCode
        versionName = AndroidSdk.versionName

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

    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(project(Libraries.Modules.common))
    implementation(project(Libraries.Modules.domain))
    implementation(project(Libraries.Modules.network))
    implementation(project(Libraries.Modules.repository))
    implementation(project(Libraries.Modules.local))

    //Ktx Core
    implementation(Libraries.AndroidX.coreKtx)

    //UI
    implementation(Libraries.AndroidX.appcompat)
    implementation(Libraries.AndroidX.Constraint.constraintLayout)
    implementation(Libraries.Google.material)
    implementation(Libraries.AndroidX.Activity.activity)

    //LifeCycle
    implementation(Libraries.AndroidX.Lifecycle.liveData)
    implementation(Libraries.AndroidX.Lifecycle.viewModel)
    implementation(Libraries.AndroidX.Lifecycle.runtime)
    kapt(Libraries.AndroidX.Lifecycle.compiler)

    //Timber
    implementation(Libraries.Timber.timber)

    //Hilt
    implementation(Libraries.Google.Hilt.android)
    kapt(Libraries.Google.Hilt.compiler)

    // Location
    implementation(Libraries.Google.servicesLocation)

    //Test
    testImplementation(Libraries.JUnit.junit)
    testImplementation(Libraries.Mockk.mockk)
    testImplementation(Libraries.Google.truth)
    testImplementation(Libraries.AndroidX.Test.archCore)
    testImplementation(Libraries.Coroutines.test)

    androidTestImplementation(Libraries.AndroidX.Test.runner)
    androidTestImplementation(Libraries.AndroidX.Test.rules)
    androidTestImplementation(Libraries.AndroidX.Test.Ext.junit)
    androidTestImplementation(Libraries.AndroidX.Test.espressoCore)
    androidTestImplementation(Libraries.Kakao.kakao)
    androidTestImplementation (Libraries.Hamcrest.hamcrest)
}