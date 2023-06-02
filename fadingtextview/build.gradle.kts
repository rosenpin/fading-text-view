plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

val bintrayRepo = "maven"
val bintrayName = "fadingtextview"

val publishedGroupId = "com.tomer"
val libraryName = "fadingtextview"
val artifact = "fadingtextview"

val libraryDescription = "A textview that changes its content automatically every few seconds Edit"

val siteUrl = "https://github.com/rosenpin/FadingTextView"
val gitUrl = "https://github.com/rosenpin/FadingTextView.git"

val libraryVersion = "2.6"

val developerId = "rosenpin"
val developerName = "Tomer Rosenfeld"
val developerEmail = "tomerosenfeld007@gmail.com"

val licenseName = "The Apache Software License, Version 2.0"
val licenseUrl = "http://www.apache.org/licenses/LICENSE-2.0.txt"
val allLicenses = listOf("Apache-2.0")

android {
    namespace = "com.tomer.fadingtextview"
    compileSdk = 32

    defaultConfig {
        minSdk = 14
        targetSdk = 32
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
