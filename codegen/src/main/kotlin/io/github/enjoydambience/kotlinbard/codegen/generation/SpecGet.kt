package io.github.enjoydambience.kotlinbard.codegen.generation

import com.squareup.kotlinpoet.FunSpec
import io.github.enjoydambience.kotlinbard.codegen.copyDeprecationOf
import io.github.enjoydambience.kotlinbard.codegen.delegatesTo
import io.github.enjoydambience.kotlinbard.createFunction
import kotlin.reflect.full.declaredMemberFunctions

/**
 * Generates functions that simply delegate to Spec companion functions.
 *
 * Derived from spec companion functions that return (their own) spec type.
 */
object SpecGet : SpecBasedFileGenerator("_Getters") {
    override fun generateFunctionsForSpec(spec: SpecInfo): List<FunSpec> =
        spec.companionClass.declaredMemberFunctions
            .filter {
                it.returnType.classifier == spec.specClass
            }
            .map { function ->
                createFunction(function.name + spec.name) {
                    copyDeprecationOf(function)
                    delegatesTo(function)
                }
            }
}
