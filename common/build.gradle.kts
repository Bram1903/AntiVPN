plugins {
    antivpn.`shadow-conventions`
    antivpn.`library-conventions`
    `av-version`
}

dependencies {
    compileOnly(libs.adventure.api)
    compileOnly(libs.snakeyaml)
    compileOnly(libs.gson)
    compileOnlyApi(libs.lombok)
    annotationProcessor(libs.lombok)
}

tasks {
    withType<JavaCompile> {
        dependsOn(generateVersionsFile)
    }

    generateVersionsFile {
        packageName = "com.deathmotion.antivpn.util"
    }
}