import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8"
//    kotlin("plugin.serialization") version "1.5.0"
}

group = "me.ccc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
//    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1") // Kotlin serialization
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}