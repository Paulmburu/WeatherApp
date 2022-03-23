object Libraries {

    object GradlePlugin {
        const val version = "7.0.4"
        const val androidGradlePlugin = "com.android.tools.build:gradle:$version"
    }

    object Kotlin {
        private const val version = "1.5.31"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"
    }

    object Coroutines {
        private const val version = "1.5.2"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }

    object OkHttp {
        private const val version = "4.9.1"
        const val okhttp = "com.squareup.okhttp3:okhttp:$version"
        const val logging = "com.squareup.okhttp3:logging-interceptor:$version"
        const val mockWebServer = "com.squareup.okhttp3:mockwebserver:$version"
    }

    object Retrofit {
        private const val version = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val gson = "com.squareup.retrofit2:converter-gson:$version"
    }

    object Jacoco {
        const val version = "0.8.7"
        const val jacocoAndroid = "com.hiya.jacoco-android"
    }

    object JUnit {
        private const val version = "4.13"
        const val junit = "junit:junit:$version"
    }

    object Google {
        const val material = "com.google.android.material:material:1.4.0"
        const val servicesLocation = "com.google.android.gms:play-services-location:19.0.1"
        const val truth = "com.google.truth:truth:1.1.3"

        object Hilt {
            private const val version = "2.39"

            const val gradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
            const val android = "com.google.dagger:hilt-android:$version"
            const val compiler = "com.google.dagger:hilt-compiler:$version"
            const val testing = "com.google.dagger:hilt-android-testing:$version"
        }
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.2.0"

        const val coreKtx = "androidx.core:core-ktx:1.6.0"

        object Activity {
            const val activityCompose = "androidx.activity:activity-compose:1.3.1"
            const val activity = "androidx.activity:activity-ktx:1.5.0-alpha02"
        }

        object Constraint {
            const val constraintLayout =
                "androidx.constraintlayout:constraintlayout:2.1.2"
        }

        object Lifecycle {
            private const val version = "2.4.0-rc01"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${version}"
            const val compiler = "androidx.lifecycle:lifecycle-compiler:${version}"
        }

        object Navigation {
            const val navigation = "androidx.navigation:navigation-compose:2.4.0-alpha10"
        }

        object Test {
            private const val version = "1.4.0"
            const val core = "androidx.test:core:$version"
            const val rules = "androidx.test:rules:$version"
            const val runner = "androidx.test:runner:$version"
            const val archCore = "androidx.arch.core:core-testing:2.1.0"

            object Ext {
                private const val version = "1.1.2"
                const val junit = "androidx.test.ext:junit-ktx:$version"
            }

            const val espressoCore = "androidx.test.espresso:espresso-core:3.4.0"
        }

        object Room {
            private const val version = "2.3.0"
            const val runtime = "androidx.room:room-runtime:$version"
            const val ktx = "androidx.room:room-ktx:$version"
            const val compiler = "androidx.room:room-compiler:$version"
        }

    }

    object Timber {
        private const val version = "5.0.1"
        const val timber = "com.jakewharton.timber:timber:$version"
    }

    object Mockk {
        private const val version = "1.12.2"
        const val mockk = "io.mockk:mockk:${version}"
    }

    object Kakao {
        private const val version = "3.0.6"
        const val kakao = "io.github.kakaocup:kakao:$version"
    }

    object Hamcrest {
        private const val version = "2.2"
        const val hamcrest = "org.hamcrest:hamcrest:$version"
    }

    object Modules {
        const val domain = ":domain"
        const val common = ":common"
        const val network = ":data:network"
        const val repository = ":data:repository"
        const val local = ":data:local"
    }

}

object AndroidSdk {
    const val minSdkVersion = 21
    private const val compileSdkVersion = 31
    const val targetSdkVersion = compileSdkVersion
    const val versionCode = 1
    const val versionName = "1.0"
    const val applicationId = "github.paulmburu.weatherapp"
}
