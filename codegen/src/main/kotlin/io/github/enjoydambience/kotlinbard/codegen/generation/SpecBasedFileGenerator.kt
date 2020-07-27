package io.github.enjoydambience.kotlinbard.codegen.generation

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec

/**
 * File generators that are based on reflecting methods on Spec classes.
 *
 * The generated FunSpecs can then be referenced from other generators.
 *
 * @see SpecInfo
 */
abstract class SpecBasedFileGenerator(
    override val fileName: String
) : FileGenerator {

    protected abstract fun generateFunctionsForSpec(spec: SpecInfo): List<FunSpec>

    val functionsBySpec by lazy {
        SpecInfo.allSpecs.associateWith(this::generateFunctionsForSpec)
    }

    override fun FileSpec.Builder.generate() {
        functionsBySpec.values.asSequence().flatten().forEach {
            addFunction(it)
        }
    }
}
