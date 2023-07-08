/*
 * This file is a part of UltraStaffChat (https://github.com/HyperaDev/UltraStaffChat).
 *
 * Copyright (C) 2021-2023 The UltraStaffChat Authors.
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
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
@Suppress( // https://youtrack.jetbrains.com/issue/KTIJ-19369/
    "DSL_SCOPE_VIOLATION",
    "MISSING_DEPENDENCY_CLASS",
    "UNRESOLVED_REFERENCE_WRONG_RECEIVER",
    "FUNCTION_CALL_EXPECTED"
)
plugins {
    id("ultrastaffchat.common")
    alias(libs.plugins.indra.sonatype)
    alias(libs.plugins.shadow)
    java
}

group = "dev.hypera"
version = "5.2.2-SNAPSHOT"
description = "A fully customisable staff communication plugin for BungeeCord!"

repositories {
    mavenCentral()
    sonatype.ossSnapshots()
    maven("https://repo.hypera.dev/releases/")
}

dependencies {
    implementation(libs.updatelib)
    implementation(libs.adventure.api)
    implementation(libs.adventure.platform.bungeecord)
    implementation(libs.bstats.bungeecord)
    compileOnly(libs.platform.bungeecord)
    compileOnly(libs.annotations)
}

indraSonatype {
    useAlternateSonatypeOSSHost("s01")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    jar {
        manifest.attributes (
            "Specification-Title" to project.name,
            "Specification-Version" to project.version,
            "Specification-Vendor" to "Hypera Development",
            "Implementation-Title" to project.name,
            "Implementation-Version" to project.version,
            "Implementation-Vendor" to "Hypera Development",
            "Implementation-Vendor-Id" to "HyperaDev",
            "Implementation-URL" to "https://www.spigotmc.org/resources/68956/",
        )
    }
    shadowJar {
        archiveFileName.set("UltraStaffChat-${project.version}.jar")
        fun relocate(pattern: String) {
            relocate(pattern, "dev.hypera.ultrastaffchat.lib.${pattern.substringAfterLast('.')}")
        }

        dependencies {
            exclude("META-INF/**")
        }

        relocate("dev.hypera.updatelib")
        relocate("net.kyori")
        relocate("org.bstats")
        relocate("com.vdurmont.semver4j")
        relocate("org.json")
    }
    build {
        dependsOn(shadowJar)
    }
}
