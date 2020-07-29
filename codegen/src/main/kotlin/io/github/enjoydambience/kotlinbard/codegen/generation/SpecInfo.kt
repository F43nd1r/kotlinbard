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

import com.squareup.kotlinpoet.*
import kotlin.reflect.KClass
import kotlin.reflect.full.companionObject

/**
 * Reflection-based info about a `XXXSpec`, such as `FileSpec`, `TypeSpec`, etc.
 *
 * CodeBlock is also included because it follows a compatible structure.
 */
class SpecInfo private constructor(
    /** Class of the spec. */
    val specClass: KClass<*>
) {
    /** Name of spec, minus "Spec" suffix */
    val name = specClass.simpleName!!.removeSuffix("Spec")
    val companionClass = specClass.companionObject!!

    /** Class of the builder of spec (e.g. `FileSpec.Builder::class`) */
    val builderClass = specClass.nestedClasses.first { it.simpleName == "Builder" }

    val builderName get() = builderClass.asClassName()

    override fun toString(): String = "SpecInfo(${specClass.simpleName})"

    companion object {
        fun of(klass: KClass<*>): SpecInfo? = allSpecsMap[klass]

        val allSpecsMap = listOf(
            FileSpec::class,
            TypeSpec::class,
            PropertySpec::class,
            FunSpec::class,
            ParameterSpec::class,
            TypeAliasSpec::class,
            AnnotationSpec::class,
            CodeBlock::class
        ).associateWith { SpecInfo(it) }

        val allSpecs get() = allSpecsMap.values
    }
}
