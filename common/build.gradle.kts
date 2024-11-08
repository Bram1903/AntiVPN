plugins {
    antivpn.`shadow-conventions`
    antivpn.`library-conventions`
}

dependencies {
    compileOnlyApi(libs.adventure.api)
    compileOnlyApi(libs.snakeyaml)
    compileOnlyApi(libs.lombok)
    annotationProcessor(libs.lombok)
}