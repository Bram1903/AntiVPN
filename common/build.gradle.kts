plugins {
    antivpn.`shadow-conventions`
    antivpn.`library-conventions`
    `av-version`
}

dependencies {
    implementation(libs.expiringmap)
    compileOnly(libs.adventure.api)
    compileOnly(libs.snakeyaml)
    compileOnly(libs.gson)
}

tasks {
    shadowJar {
        relocate("net.jodah", "com.deathmotion.antivpn.shaded")
    }

    withType<JavaCompile> {
        dependsOn(generateVersionsFile)
    }

    generateVersionsFile {
        packageName = "com.deathmotion.antivpn.util"
    }
}