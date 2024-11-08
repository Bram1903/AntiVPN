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
    shadow(libs.adventure.bungee)
    compileOnly(libs.bungeecord)
    annotationProcessor(libs.lombok)
}

tasks {
    shadowJar {
        relocate("net.kyori", "com.deathmotion.antivpn.shaded")
    }
}

