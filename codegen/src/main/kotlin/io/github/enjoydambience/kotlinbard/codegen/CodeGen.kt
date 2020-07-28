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

package io.github.enjoydambience.kotlinbard.codegen

import io.github.enjoydambience.kotlinbard.codegen.generation.*
import java.io.File
import java.nio.file.Path

/**
 * Info for code gen.
 */
object CodeGen {
    const val destinationPackage = "io.github.enjoydambience.kotlinbard"

    private const val previewModule = "codegenOutPreview"
    private const val mainModule = "kotlinbard"
    val previewSrc = File("../$previewModule/src/main/kotlin")
    val mainSrc = File("../$mainModule/src/main/generated")

    /**
     * All file generators to be used in codegen.
     */
    val allFileGenerators = listOf<FileGenerator>(
        SpecCreate,
        SpecGet,
        SpecAdd
    )

    fun generateTo(path: Path) {
        allFileGenerators.forEach {
            it.createFileSpec().writeTo(path)
        }
    }
}
