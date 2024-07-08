plugins {
    kotlin("jvm") version "2.0.0"
    id("application")
}

group = "ca.coudrew"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.xenomachina:kotlin-argparser:2.0.7")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "ca.coudrew.ccwc.MainKt"
    }
    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }

    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}


application {
    mainClass.set("ca.coudrew.ccwc.MainKt")

}
kotlin {
    jvmToolchain(21)
}