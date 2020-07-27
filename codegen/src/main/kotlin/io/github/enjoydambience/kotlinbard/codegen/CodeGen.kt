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
