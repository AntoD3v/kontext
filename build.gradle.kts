/**
 * © 2024 kontext
 * @author antodev
 */

group = "dev.kontext"
version = "1.0.0"

plugins {

    kotlin("jvm") version "2.0.0"

    id("maven-publish")

}

dependencies {

    // Kotlin <3
    kotlin("jvm")
    kotlin("reflect")

    testImplementation(kotlin("test"))

}

tasks.test {
    useJUnitPlatform()
}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(17)
}