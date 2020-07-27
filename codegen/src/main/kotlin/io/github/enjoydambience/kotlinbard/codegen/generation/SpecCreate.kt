package io.github.enjoydambience.kotlinbard.codegen.generation

import com.squareup.kotlinpoet.*
import io.github.enjoydambience.kotlinbard.addParameter
import io.github.enjoydambience.kotlinbard.codegen.copyDeprecationOf
import io.github.enjoydambience.kotlinbard.codegen.reflectCodeCall
import io.github.enjoydambience.kotlinbard.createFunction
import net.pearx.kasechange.toPascalCase
import kotlin.reflect.KFunction
import kotlin.reflect.full.declaredMemberFunctions

/**
 * Generates spec creator functions (`createXXX` functions).
 * These are called "creators" instead of "builders" to disambiguate from kotlin-poet's spec builders.
 *
 * These are derived from spec companion functions that return a builder type.
 *
 * These functions have the form `XXXSpec.createXxx(<parameters>).apply(config).build()`.
 */
object SpecCreate : SpecBasedFileGenerator("_Creators") {

    override fun generateFunctionsForSpec(spec: SpecInfo): List<FunSpec> =
        spec.companionClass.declaredMemberFunctions
            .filter {
                it.returnType.classifier == spec.builderClass
            }
            .map { function ->
                generateFunction(spec, function)
            }

    private fun generateFunction(spec: SpecInfo, function: KFunction<*>): FunSpec {
        // "xxxBuilder" -> "createXxx"
        // "builder" -> "create<spec name>"
        val funNameMinusBuilder = function.name.replace("[bB]uilder".toRegex(), "")
        val generatedName = funNameMinusBuilder
            .ifEmpty { spec.name.takeUnless { it == "Fun" } ?: "function" }
            .let {
                "create" + it.toPascalCase()
            }

        return createFunction(generatedName) {
            copyDeprecationOf(function)

            addModifiers(KModifier.INLINE)
            returns(spec.specClass)

            val (call, params) = reflectCodeCall(function)
            addParameters(params)

            val configParam = LambdaTypeName.get(
                receiver = spec.builderClass.asTypeName(),
                returnType = UNIT
            )
            addParameter("config", configParam) {
                defaultValue("{}")
            }

            addCode("return ")
            addCode(call)
            addCode(".apply(config).build()")
        }
    }
}

