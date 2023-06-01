buildscript {
    val kotlin_version = "1.6.21"
    repositories {
        jcenter()
        google()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.4.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath("com.jfrog.bintray.gradle:gradle-bintray-plugin:1.4")
        classpath("com.github.dcendents:android-maven-gradle-plugin:1.4.1")
    }
}

allprojects {
    repositories {
        jcenter()
        maven {
            url = uri("https://maven.google.com")
        }
        google()
    }
}