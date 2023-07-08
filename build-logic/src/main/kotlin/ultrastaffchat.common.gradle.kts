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
import net.ltgt.gradle.errorprone.errorprone

plugins {
    id("ultrastaffchat.publishing")
    id("net.kyori.indra")
    id("net.kyori.indra.git")
    id("net.kyori.blossom")
    id("net.ltgt.errorprone")
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

indra {
    javaVersions {
        target(8)
        testWith(8, 11, 17)
    }
}

blossom {
    replaceToken("@version@", rootProject.version)
}

dependencies {
    errorprone(libs.findLibrary("build-errorprone-core").get())
    compileOnly(libs.findLibrary("build-errorprone-annotations").get())
}

tasks.withType<JavaCompile>().configureEach {
    options.errorprone {

    }
}
