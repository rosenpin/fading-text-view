buildscript {
    val kotlin_version = "1.6.21"
    repositories {
        mavenCentral()
        google()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.0.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath("com.github.dcendents:android-maven-gradle-plugin:1.4.1")
    }
}

allprojects {
    repositories {
        maven {
            url = uri("https://jitpack.io")
        }
        mavenCentral()
        maven {
            url = uri("https://maven.google.com")
        }
        google()
    }
}