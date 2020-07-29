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

package io.github.enjoydambience.kotlinbard.codegen.generation

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.UNIT
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
object SpecBuilders : SpecFunctionFileGenerator("_SpecBuilders") {
    const val funPrefix = "create"
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
        val generatedName = if (spec.name == "Type" && funNameMinusBuilder == "annotation") {
            "annotationClass"
        } else {
            funNameMinusBuilder
                .ifEmpty { spec.name.takeUnless { it == "Fun" } ?: "function" }
        }.let {
            funPrefix + it.toPascalCase()
        }

        return createFunction(generatedName) {
            copyDeprecationOf(function)

            addModifiers(KModifier.INLINE)
            returns(spec.specClass)

            val (call, params) = reflectCodeCall(function)
            addParameters(params)

            val configParam = LambdaTypeName.get(
                receiver = spec.builderName,
                returnType = UNIT
            )
            addParameter("config", configParam) {
                defaultValue("{}")
            }

            addStatement("return %L.apply(config).build()", call)
        }
    }

}
