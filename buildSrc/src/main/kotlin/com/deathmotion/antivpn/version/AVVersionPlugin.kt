/*
 * This file is part of AntiVPN - https://github.com/Bram1903/AntiVPN
 * Copyright (C) 2024 Bram and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.deathmotion.antivpn.version

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.kotlin.dsl.getByName
import org.gradle.kotlin.dsl.register

class AVVersionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        val task = target.tasks.register<AVVersionTask>(AVVersionTask.TASK_NAME) {
            group = target.rootProject.name.toString()

            version = target.version.toString()
            outputDir = target.layout.buildDirectory.dir("generated/sources/src/java/main")
        }

        target.afterEvaluate {
            val sourceSets = target.extensions.getByName<SourceSetContainer>("sourceSets")

            sequenceOf(SourceSet.MAIN_SOURCE_SET_NAME, SourceSet.TEST_SOURCE_SET_NAME).forEach {
                sourceSets.getByName(it).java.srcDir(task.flatMap { it.outputDir })
            }

            task.get().generate()
        }
    }

}