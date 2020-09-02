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
import net.pearx.kasechange.toCamelCase
import net.pearx.kasechange.toPascalCase
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.declaredMemberFunctions

/**
 * Generates spec builder functions (`buildXXX` functions).
 *
 * These are derived from spec companion functions that return a builder type.
 *
 * These have the form `XXXSpec.xxx(<parameters>).apply(config).build()`.
 */
object SpecBuilders : SpecFunctionFileGenerator() {

    private data class BuildFunctionGroup(
        /** The name of the builder function; this is for reference by [SpecAdders] */
        val referenceName: String,
        /** The name of the generated function */
        val generatedName: String,
        /** The name of the function this delegates to */
        val delegateFunName: String,
    )

    private class BuildFunctionsScope(private val spec: SpecInfo) {
        val groups = mutableListOf<BuildFunctionGroup>()

        /**
         * @receiver reference builder function name
         * @param generatedName name of the generated function. Defaults to `add<Receiver>`
         * @param delegatesTo the name of the builder function this delegates to. Defaults to [generatedName]
         */
        fun from(
            delegatesTo: String,
            name: String = deriveDefaultName(delegatesTo, spec),
            generatedName: String = "build" + name.toPascalCase(),
        ) {
            groups += BuildFunctionGroup(name, generatedName, delegatesTo)
        }
    }

    private fun deriveDefaultName(delegateName: String, spec: SpecInfo): String {
        if (delegateName == "builder") return spec.name.toCamelCase()
        return delegateName.removeSuffix("Builder")
    }

    private val allGroups: Map<SpecInfo, List<BuildFunctionGroup>> = run {
        //local dsl setup
        val result = mutableMapOf<SpecInfo, List<BuildFunctionGroup>>()
        operator fun KClass<*>.invoke(config: BuildFunctionsScope.() -> Unit) {
            val spec = SpecInfo.of(this)!!
            result[spec] = BuildFunctionsScope(spec).apply(config).groups
        }
        FileSpec::class {
            from("builder")
        }
        TypeSpec::class {
            from("annotationBuilder", name = "annotationClass")
            from("anonymousClassBuilder")
            from("classBuilder")
            from("companionObjectBuilder")
            from("enumBuilder")
            from("expectClassBuilder")
            from("funInterfaceBuilder")
            from("interfaceBuilder")
            from("objectBuilder")
        }
        PropertySpec::class {
            from("builder")
        }
        FunSpec::class {
            from("builder", name = "function")
            from("constructorBuilder")
            from("getterBuilder")
            from("overriding")
            from("setterBuilder")
        }
        ParameterSpec::class {
            from("builder")
        }
        TypeAliasSpec::class {
            from("builder")
        }
        AnnotationSpec::class {
            from("builder")
        }
        CodeBlock::class {
            from("builder", generatedName = "codeBlock")
        }

        result
    }

    override fun generateFunctionsForSpec(spec: SpecInfo): List<FunSpec> {
        val possibleDelegates = spec.companionClass.declaredMemberFunctions
            .filter { function ->
                function.returnType.classifier == spec.builderClass
            }
            .groupBy { it.name }

        return allGroups.getValue(spec)
            .flatMap {
                (possibleDelegates[it.delegateFunName] ?: error("No builder function called ${it.delegateFunName}"))
                    .map { function ->
                        generateFunction(spec, function, it.generatedName, it.referenceName)
                    }
            }
    }

    /** Tag to store reference name in [FunSpec] */
    class ReferenceName(val name: String)

    private fun generateFunction(
        spec: SpecInfo,
        function: KFunction<*>,
        generatedName: String,
        referenceName: String,
    ): FunSpec = buildFunction(generatedName) {
        tag(ReferenceName(referenceName))
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
