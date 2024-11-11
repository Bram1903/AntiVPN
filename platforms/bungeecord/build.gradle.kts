plugins {
    antivpn.`shadow-conventions`
    antivpn.`library-conventions`
}

repositories {
    maven {
        name = "papermc" // For Brigadier
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    shadow(project(":common"))
    compileShadowOnly(libs.adventure.bungee)
    compileShadowOnly(libs.bstats.bungeecord)
    compileOnly(libs.bungeecord)
}
