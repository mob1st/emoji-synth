plugins {
    kotlin("jvm") version "2.1.20"
    application
}

group = "com.mob1st.emojis"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainClass.set("com.mob1st.emoji.cli.MainKt")
}

dependencies {
    implementation("org.json:json:20250107")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(23)
}