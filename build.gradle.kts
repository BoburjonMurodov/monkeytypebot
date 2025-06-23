plugins {
    kotlin("jvm") version "2.1.20"
}

group = "bot.boboor.monkeytype"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
//     https://mvnrepository.com/artifact/net.sourceforge.tess4j/tess4j

}

dependencies {
//    implementation("net.sourceforge.tess4j:tess4j:1.3.0")

    implementation("net.sourceforge.tess4j:tess4j:5.4.0")
//    implementation("org.slf4j:slf4j-api:1.6.1")
//    implementation("org.slf4j:slf4j-simple:1.6.1")

//    implementation("org.slf4j:slf4j-api:2.0.13")
//    implementation("org.slf4j:slf4j-simple:2.0.13")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}