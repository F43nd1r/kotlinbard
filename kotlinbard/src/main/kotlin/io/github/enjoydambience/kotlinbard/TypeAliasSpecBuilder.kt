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

package io.github.enjoydambience.kotlinbard

import com.squareup.kotlinpoet.*
import java.lang.reflect.Type
import kotlin.reflect.KClass

@Suppress("FunctionName")
public fun TypeAliasSpecBuilder(poetBuilder: TypeAliasSpec.Builder): TypeAliasSpecBuilder =
    TypeAliasSpecBuilder(poetBuilder, false)

@CodegenDsl
public class TypeAliasSpecBuilder internal constructor(
    public val poetBuilder: TypeAliasSpec.Builder,
    @Suppress("UNUSED_PARAMETER") dummy: Boolean,
) : WithAnnotationsBuilder,
    WithModifiersBuilder,
    Taggable.Builder<TypeAliasSpecBuilder> {
    public override val annotations: MutableList<AnnotationSpec> get() = poetBuilder.annotations
    public override val modifiers: MutableSet<KModifier> get() = poetBuilder.modifiers
    public override val tags: MutableMap<KClass<*>, Any> get() = poetBuilder.tags
    public val typeVariables: MutableSet<TypeVariableName> get() = poetBuilder.typeVariables

    public override fun addAnnotation(annotationSpec: AnnotationSpec) {
        poetBuilder.addAnnotation(annotationSpec = annotationSpec)
    }

    public override fun addAnnotations(annotationSpecs: Iterable<AnnotationSpec>) {
        poetBuilder.addAnnotations(annotationSpecs = annotationSpecs)
    }

    public fun addKdoc(block: CodeBlock) {
        poetBuilder.addKdoc(block = block)
    }

    public fun addKdoc(format: String, vararg args: Any) {
        poetBuilder.addKdoc(format = format, args = args)
    }

    public override fun addModifiers(vararg modifiers: KModifier) {
        poetBuilder.addModifiers(modifiers = modifiers)
    }

    public override fun addModifiers(modifiers: Iterable<KModifier>) {
        poetBuilder.addModifiers(modifiers = modifiers)
    }

    public fun addTypeVariable(typeVariable: TypeVariableName) {
        poetBuilder.addTypeVariable(typeVariable = typeVariable)
    }

    public fun addTypeVariables(typeVariables: Iterable<TypeVariableName>) {
        poetBuilder.addTypeVariables(typeVariables = typeVariables)
    }

    public fun build(): TypeAliasSpec = poetBuilder.build()
}

// -- build --

public inline fun typeAlias(
    name: String,
    type: TypeName,
    config: TypeAliasSpecBuilder.() -> Unit = {},
): TypeAliasSpec = TypeAliasSpec.builder(name = name, type = type).wrapBuilder().apply(config).build()

public inline fun typeAlias(
    name: String,
    type: Type,
    config: TypeAliasSpecBuilder.() -> Unit = {},
): TypeAliasSpec = TypeAliasSpec.builder(name = name, type = type).wrapBuilder().apply(config).build()

public inline fun typeAlias(
    name: String,
    type: KClass<*>,
    config: TypeAliasSpecBuilder.() -> Unit = {},
): TypeAliasSpec = TypeAliasSpec.builder(name = name, type = type).wrapBuilder().apply(config).build()

// -- other --

public inline fun TypeAliasSpec.modify(
    name: String = this.name,
    type: TypeName = this.type,
    config: TypeAliasSpecBuilder.() -> Unit,
): TypeAliasSpec = toBuilder(name = name, type = type).wrapBuilder().apply(config).build()
