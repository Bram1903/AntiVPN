plugins {
    `java-library`
    `maven-publish`
    `version-catalog`
}

group = rootProject.group
version = rootProject.version
description = rootProject.description

repositories {
    mavenCentral()
}

val libs = project.extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    compileOnly(libs.findLibrary("lombok").get())
    annotationProcessor(libs.findLibrary("lombok").get())
}

java {
    disableAutoTargetJvm()
}

tasks {
    withType<JavaCompile> {
        options.encoding = Charsets.UTF_8.name()
        options.release = 8
    }

    processResources {
        inputs.property("version", project.version)

        filesMatching(listOf("plugin.yml", "bungee.yml", "velocity-plugin.json")) {
            expand(
                "version" to project.version
            )
        }
    }

    jar {
        archiveClassifier = "default"
    }

    defaultTasks("build")
}

// Ensure that the latest SNAPSHOT version is always retrieved
configurations.all {
    resolutionStrategy.cacheDynamicVersionsFor(0, TimeUnit.SECONDS)
}

val taskNames = gradle.startParameter.taskNames
if (taskNames.any { it.contains("build") }
    && taskNames.any { it.contains("publish") }) {
    throw IllegalStateException("Cannot build and publish at the same time.")
}
