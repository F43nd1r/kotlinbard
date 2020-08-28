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

@file:Suppress("DeprecatedCallableAddReplaceWith", "DEPRECATION")

package io.github.enjoydambience.kotlinbard

import com.squareup.kotlinpoet.*
import java.lang.reflect.Type
import javax.lang.model.element.Modifier
import javax.lang.model.element.VariableElement
import kotlin.reflect.KClass

@Suppress("FunctionName")
public fun ParameterSpecBuilder(poetBuilder: ParameterSpec.Builder): ParameterSpecBuilder =
    ParameterSpecBuilder(poetBuilder, false)

@CodegenDsl
public class ParameterSpecBuilder internal constructor(
    public val poetBuilder: ParameterSpec.Builder,
    @Suppress("UNUSED_PARAMETER") dummy: Boolean,
) : WithKdocBuilder,
    WithAnnotationsBuilder,
    WithModifiersBuilder,
    WithJvmModifiersBuilder,
    Taggable.Builder<ParameterSpecBuilder> {
    override val annotations: MutableList<AnnotationSpec> get() = poetBuilder.annotations
    public val kdoc: CodeBlock.Builder get() = poetBuilder.kdoc
    override val modifiers: MutableList<KModifier> get() = poetBuilder.modifiers
    override val tags: MutableMap<KClass<*>, Any> get() = poetBuilder.tags

    override fun addKdoc(block: CodeBlock) {
        poetBuilder.addKdoc(block = block)
    }

    override fun addKdoc(format: String, vararg args: Any) {
        poetBuilder.addKdoc(format = format, args = args)
    }

    override fun addAnnotation(annotationSpec: AnnotationSpec) {
        poetBuilder.addAnnotation(annotationSpec = annotationSpec)
    }

    override fun addAnnotations(annotationSpecs: Iterable<AnnotationSpec>) {
        poetBuilder.addAnnotations(annotationSpecs = annotationSpecs)
    }

    override fun addModifiers(vararg modifiers: KModifier) {
        poetBuilder.addModifiers(modifiers = modifiers)
    }

    override fun addModifiers(modifiers: Iterable<KModifier>) {
        poetBuilder.addModifiers(modifiers = modifiers)
    }

    override fun jvmModifiers(modifiers: Iterable<Modifier>) {
        poetBuilder.jvmModifiers(modifiers = modifiers)
    }

    public fun defaultValue(codeBlock: CodeBlock) {
        poetBuilder.defaultValue(codeBlock = codeBlock)
    }

    public fun defaultValue(format: String, vararg args: Any?) {
        poetBuilder.defaultValue(format = format, args = args)
    }


    public fun build(): ParameterSpec = poetBuilder.build()
}

// -- build --

public inline fun parameter(
    name: String,
    type: TypeName,
    vararg modifiers: KModifier,
    config: ParameterSpecBuilder.() -> Unit = {},
): ParameterSpec = ParameterSpec.builder(name = name, type = type,
    modifiers = modifiers).asKotlinBardBuilder().apply(config).build()

public inline fun parameter(
    name: String,
    type: TypeName,
    modifiers: Iterable<KModifier>,
    config: ParameterSpecBuilder.() -> Unit = {},
): ParameterSpec = ParameterSpec.builder(name = name, type = type,
    modifiers = modifiers).asKotlinBardBuilder().apply(config).build()

public inline fun parameter(
    name: String,
    type: Type,
    vararg modifiers: KModifier,
    config: ParameterSpecBuilder.() -> Unit = {},
): ParameterSpec = ParameterSpec.builder(name = name, type = type,
    modifiers = modifiers).asKotlinBardBuilder().apply(config).build()

public inline fun parameter(
    name: String,
    type: Type,
    modifiers: Iterable<KModifier>,
    config: ParameterSpecBuilder.() -> Unit = {},
): ParameterSpec = ParameterSpec.builder(name = name, type = type,
    modifiers = modifiers).asKotlinBardBuilder().apply(config).build()

public inline fun parameter(
    name: String,
    type: KClass<*>,
    vararg modifiers: KModifier,
    config: ParameterSpecBuilder.() -> Unit = {},
): ParameterSpec = ParameterSpec.builder(name = name, type = type,
    modifiers = modifiers).asKotlinBardBuilder().apply(config).build()

public inline fun parameter(
    name: String,
    type: KClass<*>,
    modifiers: Iterable<KModifier>,
    config: ParameterSpecBuilder.() -> Unit = {},
): ParameterSpec = ParameterSpec.builder(name = name, type = type,
    modifiers = modifiers).asKotlinBardBuilder().apply(config).build()

// -- get --

@Deprecated(message =
"Element APIs don't give complete information on Kotlin types. Consider using the kotlinpoet-metadata APIs instead.")
public fun getParameter(element: VariableElement): ParameterSpec = ParameterSpec.get(element = element)

public fun unnamedParameter(type: TypeName): ParameterSpec = ParameterSpec.unnamed(type = type)

public fun unnamedParameter(type: Type): ParameterSpec = ParameterSpec.unnamed(type = type)

public fun unnamedParameter(type: KClass<*>): ParameterSpec = ParameterSpec.unnamed(type = type)

// -- other --

public inline fun ParameterSpec.modify(
    name: String = this.name,
    type: TypeName = this.type,
    config: ParameterSpecBuilder.() -> Unit,
): ParameterSpec = toBuilder(name = name, type = type).asKotlinBardBuilder().apply(config).build()
