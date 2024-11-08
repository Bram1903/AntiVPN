plugins {
    antivpn.`shadow-conventions`
    antivpn.`library-conventions`
    `av-version`
}

dependencies {
    compileOnlyApi(libs.adventure.api)
    compileOnlyApi(libs.snakeyaml)
    compileOnlyApi(libs.lombok)
    compileOnlyApi(libs.gson)
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