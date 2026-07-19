plugins {
    id("java")
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(libs.mybatis)
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.launcher)
}

tasks.test {
    useJUnitPlatform()
}