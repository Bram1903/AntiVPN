plugins {
    antivpn.`shadow-conventions`
    antivpn.`library-conventions`
    alias(libs.plugins.run.paper)
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    shadow(project(":common"))
    shadow(libs.adventure.bukkit)
    compileOnly(libs.paper)
}

tasks {
    shadowJar {
        relocate("net.kyori", "com.deathmotion.antivpn.shaded")
    }

    // 1.8.8 - 1.16.5 = Java 8
    // 1.17           = Java 16
    // 1.18 - 1.20.4  = Java 17
    // 1-20.5+        = Java 21
    val version = "1.21.3"
    val javaVersion = JavaLanguageVersion.of(21)

    val jvmArgsExternal = listOf(
        "-Dcom.mojang.eula.agree=true"
    )

    val sharedPlugins = runPaper.downloadPluginsSpec {
        url("https://github.com/ViaVersion/ViaVersion/releases/download/5.1.1/ViaVersion-5.1.1.jar")
        url("https://github.com/ViaVersion/ViaBackwards/releases/download/5.1.1/ViaBackwards-5.1.1.jar")
    }

    runServer {
        minecraftVersion(version)
        runDirectory = rootDir.resolve("run/paper/$version")

        javaLauncher = project.javaToolchains.launcherFor {
            languageVersion = javaVersion
        }

        downloadPlugins {
            from(sharedPlugins)
            url("https://ci.ender.zone/job/EssentialsX/lastSuccessfulBuild/artifact/jars/EssentialsX-2.21.0-dev+121-f7a8f86.jar")
            url("https://ci.lucko.me/job/spark/462/artifact/spark-bukkit/build/libs/spark-1.10.116-bukkit.jar")
            url("https://download.luckperms.net/1560/bukkit/loader/LuckPerms-Bukkit-5.4.145.jar")
            url("https://ci.dmulloy2.net/job/ProtocolLib/lastSuccessfulBuild/artifact/build/libs/ProtocolLib.jar")
            url("https://github.com/TCPShield/RealIP/releases/download/2.8.1/TCPShield-2.8.1.jar")
        }

        jvmArgs = jvmArgsExternal
    }

    runPaper.folia.registerTask {
        minecraftVersion(version)
        runDirectory = rootDir.resolve("run/folia/$version")

        javaLauncher = project.javaToolchains.launcherFor {
            languageVersion = javaVersion
        }

        downloadPlugins {
            from(sharedPlugins)
        }

        jvmArgs = jvmArgsExternal
    }
}

