package io.github.enjoydambience.kotlinbard.codegen

import java.nio.file.Paths


/**
 * This is meant to be run from gradle.
 *
 * Single argument = directory to generate to.
 */
fun main(args: Array<String>) {
    val pathArg = args.firstOrNull()
        ?: throw IllegalArgumentException("Specify a directory to generate code to.")
    val path = Paths.get(pathArg)
    println("generating to $path")
    CodeGen.generateTo(path)
}
