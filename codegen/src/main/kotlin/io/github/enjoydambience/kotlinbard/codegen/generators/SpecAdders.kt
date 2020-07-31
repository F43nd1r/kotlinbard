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

package io.github.enjoydambience.kotlinbard.codegen.generators

import com.squareup.kotlinpoet.*
import io.github.enjoydambience.kotlinbard.buildFunction
import io.github.enjoydambience.kotlinbard.codegen.SpecInfo
import io.github.enjoydambience.kotlinbard.codegen.codeCallNoReceiver
import net.pearx.kasechange.toPascalCase
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberFunctions

// "builder" refers to XXXSpec.Builder
// "creator" refers to functions made by SpecCreate
/**
 * Generates `addXXX` extension functions for spec builders. These create a spec using a
 * [spec creator][SpecBuilders], using a given config, then add them using the builder.
 *
 * These are derived from spec builder functions that take a single spec as a parameter, and return
 * (its own) builder type.
 *
 * These functions have the form `addXXX(creatorFunction(<params>,config))`
 */
object SpecAdders : SpecFunctionFileGenerator("_SpecAdders") {
    /**
     * Represents a _group_ of "add" functions.
     * All functions that match all the name.
     */
    private data class AddFunctionGroup(
        /** The name of the creator function to use ([SpecBuilders]] */
        val creatorFunName: String,
        /** The name of the resulting generated function */
        val generatedName: String,
        /** The name of the builder function this delegates to */
        val builderFunName: String
    )

    private class GroupBuildingScope {
        val mappings = mutableListOf<AddFunctionGroup>()

        /**
         * Adds a [AddFunctionGroup].
         *
         * @receiver name of creator function (in [SpecBuilders]), without the "create" prefix.
         * @param generatedName name of the generated function. Defaults to `add<Receiver>`
         * @param delegatesTo the name of the builder function this delegates to. Defaults to [generatedName]
         */
        operator fun String.invoke(
            //receiver = creatorFunName
            generatedName: String = "add" + this.toPascalCase(),
            delegatesTo: String = generatedName
        ) {
            mappings += AddFunctionGroup(SpecBuilders.funPrefix + this.toPascalCase(), generatedName, delegatesTo)
        }
    }


    private val allAddGroups: Map<SpecInfo, List<AddFunctionGroup>> = run {
        //local dsl setup
        val result = mutableMapOf<SpecInfo, List<AddFunctionGroup>>()
        operator fun KClass<*>.invoke(config: GroupBuildingScope.() -> Unit) {
            val spec = SpecInfo.of(this)!!
            result[spec] = GroupBuildingScope().apply(config).mappings
        }

        FileSpec::class {
            "annotation"()
            "function"()
            "property"()
            "annotationClass"(delegatesTo = "addType")
            "anonymousClass"(delegatesTo = "addType")
            "class"(delegatesTo = "addType")
            "enum"(delegatesTo = "addType")
            "expectClass"(delegatesTo = "addType")
            "funInterface"(delegatesTo = "addType")
            "interface"(delegatesTo = "addType")
            "object"(delegatesTo = "addType")
            "typeAlias"()
        }
        TypeSpec::class {
            "annotation"()
            "function"()
            "property"()
            "annotationClass"(delegatesTo = "addType")
            "anonymousClass"(delegatesTo = "addType")
            "class"(delegatesTo = "addType")
            "enum"(delegatesTo = "addType")
            "expectClass"(delegatesTo = "addType")
            "funInterface"(delegatesTo = "addType")
            "interface"(delegatesTo = "addType")
            "object"(delegatesTo = "addType")
            "constructor"(generatedName = "primaryConstructor")
            "constructor"(generatedName = "addConstructor", delegatesTo = "addFunction")
            "codeBlock"(generatedName = "init", delegatesTo = "addInitializerBlock")
            //no overriding method; that is deprecated
        }
        PropertySpec::class {
            "annotation"()
            "getter"(generatedName = "get", delegatesTo = "getter")
            //setter is handled separately
        }
        FunSpec::class {
            "annotation"()
            "parameter"()
            "codeBlock"(generatedName = "addCode", delegatesTo = "addCode")
        }
        ParameterSpec::class {
            "annotation"()
        }
        TypeAliasSpec::class {
            "annotation"()
        }

        result
    }

    override fun generateFunctionsForSpec(spec: SpecInfo): List<FunSpec> {
        val builderFunctions = spec.builderClass.declaredMemberFunctions

        return (allAddGroups[spec] ?: return emptyList())
            .associateWith { group ->
                // find builder function, with same name, 1 parameter, that 1 parameter is spec type
                // The first parameter is the `this` parameter
                val builddSpec = builderFunctions.asSequence()
                    .mapNotNull { function ->
                        if (!(function.name == group.builderFunName
                                    && function.parameters.size == 2)
                        ) return@mapNotNull null
                        val paramClass = (function.parameters[1].type.classifier as? KClass<*>)
                            ?: return@mapNotNull null
                        return@mapNotNull SpecInfo.of(paramClass) //null if not exist
                    }.singleOrNull()
                    ?: error("Cannot find function ${group.builderFunName} in ${spec.builderClass.qualifiedName}")
                // get all creator functions that create that type, and have the requested name.
                val creatorFuns = SpecBuilders.functionsBySpec.getValue(builddSpec)
                    .filter { creatorFun ->
                        creatorFun.name == group.creatorFunName
                    }
                check(creatorFuns.isNotEmpty()) { "No matching mappings for for $spec, $group" }
                return@associateWith creatorFuns
            }
            .flatMap { (mapping, creatorFuns) ->
                creatorFuns.map { creatorFun ->
                    generateFunction(
                        generatedName = mapping.generatedName,
                        builderSpec = spec,
                        delegatesTo = mapping.builderFunName,
                        creatorFun = creatorFun
                    )
                }
            }

    }

    private fun generateFunction(
        generatedName: String,
        builderSpec: SpecInfo,
        delegatesTo: String,
        creatorFun: FunSpec
    ): FunSpec = buildFunction(generatedName) {
        addModifiers(KModifier.INLINE)
        receiver(builderSpec.builderName)
        // Exclude the last config parameter; add our own so still keeps de
        addParameters(creatorFun.parameters)
        val builderCall = codeCallNoReceiver(creatorFun)

        addStatement("%N(%L)", delegatesTo, builderCall)
    }
}
