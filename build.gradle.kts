plugins {
    java
    application
    `maven-publish`
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {
    implementation("com.pulumi:pulumi:(,1.0]")
    implementation("com.pulumi:kubernetes:3.19.1")
}

group = "com.pulumi"
version = "1.0-SNAPSHOT"
description = "quickstart-java"
java.sourceCompatibility = JavaVersion.VERSION_1_10

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

application {
    mainClass.set("myproject.App")
}
