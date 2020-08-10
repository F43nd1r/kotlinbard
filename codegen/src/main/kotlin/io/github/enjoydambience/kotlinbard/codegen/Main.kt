/*
 * Copyright (c) 2020 Benjamin Ye
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.enjoydambience.kotlinbard.codegen

import com.squareup.kotlinpoet.FileSpec
import io.github.enjoydambience.kotlinbard.buildFile
import io.github.enjoydambience.kotlinbard.codegen.generators.*
import java.nio.file.Path
import java.nio.file.Paths


/**
 * This is meant to be run from gradle.
 *
 * Argument = directory to generate to.
 */
fun main(args: Array<String>) {
    val pathArg = args.firstOrNull()
        ?: throw IllegalArgumentException("Specify a directory to generate code to.")
    val path = Paths.get(pathArg)
    println("generating to $path")
    allFileGenerators.forEach {
        it.generateTo(path)
    }
}

private val allFileGenerators = listOf<FileGenerator>(
    BuilderTypeAliases,
    SpecBuilders,
    SpecGetters,
    SpecAdders,
    SpecModifiers
)

const val destinationPackage = "io.github.enjoydambience.kotlinbard"

fun FileGenerator.generateTo(path: Path) = createFileSpec().writeTo(path)

fun FileGenerator.createFileSpec(): FileSpec = buildFile(destinationPackage, fileName) {
    header()
    generate()
}
