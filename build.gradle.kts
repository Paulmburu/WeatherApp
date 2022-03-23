// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://plugins.gradle.org/m2/")
    }
    val jacocoVersion by extra("0.2")
    dependencies {
        classpath(Libraries.GradlePlugin.androidGradlePlugin)
        classpath(Libraries.Kotlin.gradlePlugin)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
        classpath(Libraries.Google.Hilt.gradlePlugin)
        classpath("com.android.tools.build:gradle:7.0.3")
        classpath("com.hiya:jacoco-android:$jacocoVersion")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}