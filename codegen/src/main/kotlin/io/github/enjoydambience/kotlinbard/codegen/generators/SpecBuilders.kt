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

package io.github.enjoydambience.kotlinbard.codegen.generators

import com.squareup.kotlinpoet.*
import io.github.enjoydambience.kotlinbard.addParameter
import io.github.enjoydambience.kotlinbard.buildFunction
import io.github.enjoydambience.kotlinbard.codegen.SpecInfo
import io.github.enjoydambience.kotlinbard.codegen.codeCallReflected
import io.github.enjoydambience.kotlinbard.codegen.copyDeprecationOf
import net.pearx.kasechange.toPascalCase
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.declaredMemberFunctions

/**
 * Generates spec builder functions (`buildXXX` functions).
 *
 * These are derived from spec companion functions that return a builder type.
 *
 * These functions have the form `XXXSpec.xxx(<parameters>).apply(config).build()`.
 */
object SpecBuilders : SpecFunctionFileGenerator() {
    private val nameMappings = mutableMapOf<SpecInfo, Map<String, String>>()

    init {
        FileSpec::class(
            "builder" to "file",
        )
        TypeSpec::class(
            "annotationBuilder" to "annotationClass",
            "anonymousClassBuilder" to "anonymousClass",
            "classBuilder" to "class",
            "companionObjectBuilder" to "companionObject",
            "enumBuilder" to "enum",
            "expectClassBuilder" to "expectClass",
            "funInterfaceBuilder" to "funInterface",
            "interfaceBuilder" to "interface",
            "objectBuilder" to "object",
        )
        PropertySpec::class(
            "builder" to "property",
        )
        FunSpec::class(
            "builder" to "function",
            "constructorBuilder" to "constructor",
            "getterBuilder" to "getter",
            "overriding" to "overriding",
            "setterBuilder" to "setter",
        )
        ParameterSpec::class(
            "builder" to "parameter",
        )
        TypeAliasSpec::class(
            "builder" to "typeAlias",
        )
        AnnotationSpec::class(
            "builder" to "annotation",
        )
        CodeBlock::class(
            "builder" to "codeBlock",
        )
    }

    private operator fun KClass<*>.invoke(vararg pairs: Pair<String, String>) {
        nameMappings[SpecInfo.of(this)!!] = mapOf(*pairs)
    }

    const val funPrefix = "build"
    override fun generateFunctionsForSpec(spec: SpecInfo): List<FunSpec> {
        val specNameMappings = nameMappings[spec]!!
        return spec.companionClass.declaredMemberFunctions
            .mapNotNull { function ->
                if (function.returnType.classifier != spec.builderClass) return@mapNotNull null
                specNameMappings[function.name]
                    .also {
                        if (it == null) println("no mapping for $function")
                    }
                    ?.let { name ->
                        generateFunction(spec, function, name)
                    }
            }
    }

    private fun generateFunction(
        spec: SpecInfo,
        function: KFunction<*>,
        nameWithoutPrefix: String
    ): FunSpec = buildFunction(funPrefix + nameWithoutPrefix.toPascalCase()) {
        tag(function)
        copyDeprecationOf(function)

        addModifiers(KModifier.INLINE)
        returns(spec.specClass)

        val (call, params) = codeCallReflected(function)
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
