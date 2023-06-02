plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.dokka") version "1.8.10"
}

tasks.dokkaHtml.configure {
    outputDirectory.set(buildDir.resolve("../../docs"))
    suppressInheritedMembers.set(true)
}

android {
    namespace = "com.tomer.fadingtextview"
    compileSdk = 32

    defaultConfig {
        minSdk = 14
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.4.2")
    testImplementation("junit:junit:4.12")
}
