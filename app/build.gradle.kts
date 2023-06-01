import org.gradle.api.JavaVersion

plugins {
    id("com.android.application")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.tomerrosenfeld.fadingtextview"
        minSdk = 23
        targetSdk = 33
        vectorDrawables.useSupportLibrary = true
        versionCode = 2
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    androidTestImplementation("androidx.test.espresso:espresso-core:3.1.0") {
        exclude(group = "com.android.support", module = "support-annotations")
    }
    implementation(project(":fadingtextview"))
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("org.adw.library:discrete-seekbar:1.0.1")
    testImplementation("junit:junit:4.13.2")
}