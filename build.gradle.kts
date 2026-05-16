plugins {
    kotlin("jvm") version "1.9.24"
    kotlin("plugin.serialization") version "1.9.24"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"
repositories { mavenCentral() }

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:1.9.24")  // ← ДОБАВИТЬ ЭТУ СТРОКУ
}
tasks.test { useJUnitPlatform() }
kotlin { jvmToolchain(17) }

application {
    mainClass.set("MainKt")
}