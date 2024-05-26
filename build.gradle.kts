/**
 * © 2024 kontext
 * @author antodev
 */

group = "dev.kontext"
version = "1.0.0"

plugins {

    id("org.jetbrains.kotlin.jvm") version("2.0.0")
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