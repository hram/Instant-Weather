// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()

    }
    dependencies {
        classpath(Plugins.gradleAndroid)
        classpath(Plugins.kotlinGradlePlugin)
        classpath(Plugins.safeArgs)
        classpath(Plugins.crashlyticsPlugin)
        classpath(Plugins.marathon)
    }
}

plugins {
    id("org.jlleitschuh.gradle.ktlint") version ("9.3.0")
}

allprojects {
    repositories {
        google()
        jcenter()
        maven(url = "https://jitpack.io")
    }
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}

tasks.register("clean").configure {
    delete("build")
}