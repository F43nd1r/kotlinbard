/*
 *    Copyright 2020 Benjamin Ye
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

plugins {
    kotlin("jvm")
}
repositories {
    maven("https://dl.bintray.com/enjoydambience/maven")
}

dependencies {
    implementation(kotlin("stdlib-jdk8", version = Versions.Kotlin.stdlib))
    implementation(kotlin("reflect", version = Versions.Kotlin.stdlib))
    implementation(Deps.kotlinPoet)
    implementation(Deps.kaseChange)
    implementation("io.github.enjoydambience:kotlinbard:0.0.1")

    testImplementation(Deps.Test.jUnit)
    testImplementation(Deps.Test.Kotest.runner)
    testImplementation(Deps.Test.Kotest.assertions)
    testImplementation(Deps.Test.Kotest.console)
}

val mainClass by ext("io.github.enjoydambience.kotlinbard.codegen.MainKt")
