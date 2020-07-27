package io.github.enjoydambience.kotlinbard.codegen.generation

import com.squareup.kotlinpoet.FileSpec
import io.github.enjoydambience.kotlinbard.addAnnotation
import io.github.enjoydambience.kotlinbard.codegen.CodeGen
import io.github.enjoydambience.kotlinbard.createFile

/**
 * Generates a file.
 *
 * This interface allows for some common configuration between generated files.
 */
interface FileGenerator {
    val fileName: String
    fun FileSpec.Builder.generate()

    /**
     * Gets the name of the source file that contains this generator.
     *
     * This is used in the "this file is auto generated" comment.
     */
    val generatorSourceFileName: String
        get() = this::class.simpleName!! + ".kt"
}

fun FileGenerator.createFileSpec(): FileSpec = createFile(CodeGen.destinationPackage, fileName) {
    addComment(
        """
        NOTE: This file is auto generated from $generatorSourceFileName
        and should not be modified by hand.
    """.trimIndent()
    )
    addAnnotation(Suppress::class) {
        listOf(
            "NO_EXPLICIT_VISIBILITY_IN_API_MODE_WARNING",
            "unused",
            "DEPRECATION",
            "DeprecatedCallableAddReplaceWith"
        ).forEach {
            addMember("%S", it)
        }
    }

    generate()
}


