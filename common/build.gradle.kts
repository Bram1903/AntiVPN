plugins {
    antivpn.`shadow-conventions`
    antivpn.`library-conventions`
    `av-version`
}

dependencies {
    compileShadowOnly(libs.expiringmap)
    compileOnly(libs.adventure.api)
    compileOnly(libs.snakeyaml)
    compileOnly(libs.gson)
}

tasks {
    withType<JavaCompile> {
        dependsOn(generateVersionsFile)
    }

    generateVersionsFile {
        packageName = "com.deathmotion.antivpn.util"
    }
}