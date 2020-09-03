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
import io.github.enjoydambience.kotlinbard.buildFunction
import io.github.enjoydambience.kotlinbard.codegen.SpecInfo
import io.github.enjoydambience.kotlinbard.codegen.codeCall
import net.pearx.kasechange.toPascalCase
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberFunctions

// "builder" refers to XXXSpec.Builder
// "creator" refers to functions made by SpecCreate
/**
 * Generates extension functions for builders corresponding to a combination of adding and building.
 *
 * These are derived from spec builder functions that take a single spec as a parameter.
 *
 * These have the form `addXXX(buildXXX(...))`
 */
object SpecAdders : SpecFunctionFileGenerator() {

    data class AddFunctionGroup(
        /** The name of the creator function to use ([SpecBuilders]] */
        val builderFunName: String,
        /** The name of the resulting generated function */
        val generatedName: String,
        /** The name of the builder function this delegates to */
        val delegateFunName: String,
    )

    private class AddFunctionsScope {
        val groups = mutableListOf<AddFunctionGroup>()

        /**
         * @receiver reference builder function name (in [SpecBuilders])
         * @param generatedName name of the generated function. Defaults to `add<Receiver>`
         * @param delegatesTo the name of the builder function this delegates to. Defaults to [generatedName]
         */
        fun builds(
            builderFunName: String,
            generatedName: String = "add" + builderFunName.toPascalCase(),
            delegatesTo: String = generatedName,
        ) {
            groups += AddFunctionGroup(builderFunName, generatedName, delegatesTo)
        }
    }

    val allGroups: Map<SpecInfo, List<AddFunctionGroup>> = run {
        //local dsl setup
        val result = mutableMapOf<SpecInfo, List<AddFunctionGroup>>()
        operator fun KClass<*>.invoke(config: AddFunctionsScope.() -> Unit) {
            val spec = SpecInfo.of(this)!!
            result[spec] = AddFunctionsScope().apply(config).groups
        }

        FileSpec::class {
            builds("annotation")
            builds("function")
            builds("property")
            builds("annotationClass", delegatesTo = "addType")
            builds("anonymousClass", delegatesTo = "addType")
            builds("class", delegatesTo = "addType")
            builds("enum", delegatesTo = "addType")
            builds("expectClass", delegatesTo = "addType")
            builds("funInterface", delegatesTo = "addType")
            builds("interface", delegatesTo = "addType")
            builds("object", delegatesTo = "addType")
            builds("typeAlias")
        }
        TypeSpec::class {
            builds("annotation")
            builds("function")
            builds("property")
            builds("annotationClass", delegatesTo = "addType")
            builds("anonymousClass", delegatesTo = "addType")
            builds("class", delegatesTo = "addType")
            builds("enum", delegatesTo = "addType")
            builds("expectClass", delegatesTo = "addType")
            builds("funInterface", delegatesTo = "addType")
            builds("interface", delegatesTo = "addType")
            builds("object", delegatesTo = "addType")
            builds("constructor", generatedName = "primaryConstructor")
            builds("constructor", generatedName = "addConstructor", delegatesTo = "addFunction")
            builds("codeBlock", generatedName = "addInitializerBlock")
            builds("codeBlock", generatedName = "addSuperclassConstructorParameter")
            builds("codeBlock", generatedName = "addKdoc")
        }
        PropertySpec::class {
            builds("annotation")
            builds("getter", generatedName = "getter")
            builds("setter", generatedName = "setter")
            builds("codeBlock", generatedName = "delegate")
            builds("codeBlock", generatedName = "initializer")
            builds("codeBlock", generatedName = "addKdoc")
        }
        FunSpec::class {
            builds("annotation")
            builds("parameter")
            builds("codeBlock", generatedName = "addCode")
            builds("codeBlock", generatedName = "addKdoc")
        }
        ParameterSpec::class {
            builds("annotation")
            builds("codeBlock", generatedName = "defaultValue")
            builds("codeBlock", generatedName = "addKdoc")
        }
        AnnotationSpec::class{
            builds("codeBlock", generatedName = "addMember")
        }
        TypeAliasSpec::class {
            builds("annotation")
            builds("codeBlock", generatedName = "addKdoc")
        }
        result
    }

    override fun generateFunctionsForSpec(spec: SpecInfo): List<FunSpec> {
        val possibleAdders = addersFrom(spec)

        return allGroups[spec]
            ?.flatMap { group ->
                val addingSpec = possibleAdders[group.delegateFunName]
                    ?: error("No builder function of ${spec.name} named ${group.delegateFunName}")
                SpecBuilders.functionsBySpec.getValue(addingSpec)
                    .filter {
                        it.tag<SpecBuilders.ReferenceName>()?.name == group.builderFunName
                    }
                    .also {
                        if (it.isEmpty()) {
                            error("No builder functions that return ${addingSpec.name} named ${group.builderFunName}")
                        }
                    }
                    .map { builderFun ->
                        generateFunction(
                            generatedName = group.generatedName,
                            builderSpec = spec,
                            delegatesTo = group.delegateFunName,
                            builderFun = builderFun
                        )
                    }
            }
            .orEmpty()
    }

    fun addersFrom(spec: SpecInfo): Map<String, SpecInfo> {
        return spec.builderClass.declaredMemberFunctions.asSequence()
            //find functions with a single parameter that is a spec type
            //associate function's name to spec type
            .filter { it.parameters.size == 2 } //first parameter is "this"
            .mapNotNull {
                val parameterSpec =
                    (it.parameters[1].type.classifier as? KClass<*>)?.let { klass -> SpecInfo.of(klass) }
                        ?: return@mapNotNull null
                it to parameterSpec
            }
            .associate { (function, spec) -> function.name to spec }
    }

    private fun generateFunction(
        generatedName: String,
        builderSpec: SpecInfo,
        delegatesTo: String,
        builderFun: FunSpec,
    ): FunSpec = buildFunction(generatedName) {
        addModifiers(KModifier.INLINE)
        receiver(builderSpec.builderName)
        addParameters(builderFun.parameters)
        val builderCall = codeCall(builderFun)
        addStatement("%N(%L)", delegatesTo, builderCall)
    }
}
