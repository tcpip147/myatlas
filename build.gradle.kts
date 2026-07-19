group = "com.look4counter"
version = "1.0-SNAPSHOT"

subprojects {
    tasks.withType<JavaCompile> {
        sourceCompatibility = "25"
        targetCompatibility = "25"
        options.encoding = "UTF-8"
    }
}