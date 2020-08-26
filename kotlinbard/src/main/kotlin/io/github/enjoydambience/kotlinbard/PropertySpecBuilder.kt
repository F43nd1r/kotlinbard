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
import javax.lang.model.element.Element
import kotlin.reflect.KClass

@Suppress("FunctionName")
public fun PropertySpecBuilder(poetBuilder: PropertySpec.Builder): PropertySpecBuilder =
    PropertySpecBuilder(poetBuilder, false)

@CodegenDsl
public class PropertySpecBuilder internal constructor(
    public val poetBuilder: PropertySpec.Builder,
    @Suppress("UNUSED_PARAMETER") dummy: Boolean,
) : Taggable.Builder<PropertySpecBuilder>,
    OriginatingElementsHolder.Builder<PropertySpecBuilder> {
    public val annotations: MutableList<AnnotationSpec> get() = poetBuilder.annotations
    public val modifiers: MutableList<KModifier> get() = poetBuilder.modifiers
    override val originatingElements: MutableList<Element> get() = poetBuilder.originatingElements
    public override val tags: MutableMap<KClass<*>, Any> get() = poetBuilder.tags
    public val typeVariables: MutableList<TypeVariableName> get() = poetBuilder.typeVariables
    public fun addAnnotation(annotationSpec: AnnotationSpec) {
        poetBuilder.addAnnotation(annotationSpec = annotationSpec)
    }

    public fun addAnnotation(annotation: ClassName) {
        poetBuilder.addAnnotation(annotation = annotation)
    }

    public fun addAnnotation(annotation: Class<*>) {
        poetBuilder.addAnnotation(annotation = annotation)
    }

    public fun addAnnotation(annotation: KClass<*>) {
        poetBuilder.addAnnotation(annotation = annotation)
    }

    public fun addAnnotations(annotationSpecs: Iterable<AnnotationSpec>) {
        poetBuilder.addAnnotations(annotationSpecs = annotationSpecs)
    }

    public fun addKdoc(block: CodeBlock) {
        poetBuilder.addKdoc(block = block)
    }

    public fun addKdoc(format: String, vararg args: Any) {
        poetBuilder.addKdoc(format = format, args = args)
    }

    public fun addModifiers(vararg modifiers: KModifier) {
        poetBuilder.addModifiers(modifiers = modifiers)
    }

    public fun addModifiers(modifiers: Iterable<KModifier>) {
        poetBuilder.addModifiers(modifiers = modifiers)
    }

    public fun addTypeVariable(typeVariable: TypeVariableName) {
        poetBuilder.addTypeVariable(typeVariable = typeVariable)
    }

    public fun addTypeVariables(typeVariables: Iterable<TypeVariableName>) {
        poetBuilder.addTypeVariables(typeVariables = typeVariables)
    }

    public fun build(): PropertySpec = poetBuilder.build()
    public fun delegate(codeBlock: CodeBlock) {
        poetBuilder.delegate(codeBlock = codeBlock)
    }

    public fun delegate(format: String, vararg args: Any?) {
        poetBuilder.delegate(format = format, args = args)
    }

    public fun getter(getter: FunSpec) {
        poetBuilder.getter(getter = getter)
    }

    public fun initializer(codeBlock: CodeBlock) {
        poetBuilder.initializer(codeBlock = codeBlock)
    }

    public fun initializer(format: String, vararg args: Any?) {
        poetBuilder.initializer(format = format, args = args)
    }

    public fun mutable(mutable: Boolean = true) {
        poetBuilder.mutable(mutable = mutable)
    }

    public fun receiver(receiverType: TypeName) {
        poetBuilder.receiver(receiverType = receiverType)
    }

    public fun receiver(receiverType: Type) {
        poetBuilder.receiver(receiverType = receiverType)
    }

    public fun receiver(receiverType: KClass<*>) {
        poetBuilder.receiver(receiverType = receiverType)
    }

    public fun setter(setter: FunSpec) {
        poetBuilder.setter(setter = setter)
    }
}

// -- build --

public inline fun property(
    name: String,
    type: TypeName,
    vararg modifiers: KModifier,
    config: PropertySpecBuilder.() -> Unit = {},
): PropertySpec = PropertySpec.builder(name = name, type = type,
    modifiers = modifiers).wrapBuilder().apply(config).build()

public inline fun property(
    name: String,
    type: TypeName,
    modifiers: Iterable<KModifier>,
    config: PropertySpecBuilder.() -> Unit = {},
): PropertySpec = PropertySpec.builder(name = name, type = type,
    modifiers = modifiers).wrapBuilder().apply(config).build()

public inline fun property(
    name: String,
    type: Type,
    vararg modifiers: KModifier,
    config: PropertySpecBuilder.() -> Unit = {},
): PropertySpec = PropertySpec.builder(name = name, type = type,
    modifiers = modifiers).wrapBuilder().apply(config).build()

public inline fun property(
    name: String,
    type: Type,
    modifiers: Iterable<KModifier>,
    config: PropertySpecBuilder.() -> Unit = {},
): PropertySpec = PropertySpec.builder(name = name, type = type,
    modifiers = modifiers).wrapBuilder().apply(config).build()

public inline fun property(
    name: String,
    type: KClass<*>,
    vararg modifiers: KModifier,
    config: PropertySpecBuilder.() -> Unit = {},
): PropertySpec = PropertySpec.builder(name = name, type = type,
    modifiers = modifiers).wrapBuilder().apply(config).build()

public inline fun property(
    name: String,
    type: KClass<*>,
    modifiers: Iterable<KModifier>,
    config: PropertySpecBuilder.() -> Unit = {},
): PropertySpec = PropertySpec.builder(name = name, type = type,
    modifiers = modifiers).wrapBuilder().apply(config).build()

// -- other --

public inline fun PropertySpec.modify(
    name: String = this.name,
    type: TypeName = this.type,
    config: PropertySpecBuilder.() -> Unit,
): PropertySpec = toBuilder(name = name, type = type).wrapBuilder().apply(config).build()
