/**
 * © 2024 kontext
 * @author antodev
 */

group = "dev.kontext"
version = "1.0"

plugins {

    kotlin("jvm")
    id("maven-publish")
}

dependencies {

    api(kotlin("stdlib-jdk8"))
    api("org.jetbrains.kotlin:kotlin-reflect")

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