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

import io.github.enjoydambience.kotlinbard.codegen.generators.FileGenerator
import io.kotest.core.spec.style.StringSpec
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path

const val generatedSrc = "build/generated-src"

private suspend fun FileGenerator.manualGenerate(): Unit = coroutineScope {
    val path = Path.of(generatedSrc).toAbsolutePath()
    println("generating to $path")
    val content = async {
        StringBuilder()
            .apply { generateFileSpec().writeTo(this) }
            .replace("public get\\(\\)".toRegex(), "get()")
            .replace("\n *get".toRegex(), " get")
            .replace("((get\\(\\)|fun).*\n)\n".toRegex(), "$1")
            .replace("\n\n", "\n")
    }
    withContext(Dispatchers.IO) {
        Files.createDirectories(path)
        val outputPath = path.resolve("${fileName}.kt")
        OutputStreamWriter(
            Files.newOutputStream(outputPath),
            StandardCharsets.UTF_8
        ).use { it.append(content.await()) }
    }
    Unit
}

abstract class ManualFileGenerator : StringSpec({
    this as FileGenerator
    "generate" { manualGenerate() }
}), FileGenerator
