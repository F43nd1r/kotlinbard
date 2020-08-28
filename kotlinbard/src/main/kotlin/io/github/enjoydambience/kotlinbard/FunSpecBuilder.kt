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
import javax.lang.model.element.Element
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.Modifier
import javax.lang.model.type.DeclaredType
import javax.lang.model.util.Types
import kotlin.reflect.KClass

@Suppress("FunctionName")
public fun FunSpecBuilder(poetBuilder: FunSpec.Builder): FunSpecBuilder =
    FunSpecBuilder(poetBuilder, false)

@CodegenDsl
public class FunSpecBuilder internal constructor(
    public val poetBuilder: FunSpec.Builder,
    @Suppress("UNUSED_PARAMETER") dummy: Boolean,
) : CodeBuilding(),
    WithKdocBuilder,
    WithAnnotationsBuilder,
    WithModifiersBuilder,
    WithJvmModifiersBuilder,
    Taggable.Builder<FunSpecBuilder>,
    OriginatingElementsHolder.Builder<FunSpecBuilder> {
    public override val annotations: MutableList<AnnotationSpec> get() = poetBuilder.annotations
    public override val modifiers: MutableList<KModifier> get() = poetBuilder.modifiers
    override val originatingElements: MutableList<Element> get() = poetBuilder.originatingElements
    public val parameters: MutableList<ParameterSpec> get() = poetBuilder.parameters
    public override val tags: MutableMap<KClass<*>, Any> get() = poetBuilder.tags
    public val typeVariables: MutableList<TypeVariableName> get() = poetBuilder.typeVariables

    public override fun addKdoc(block: CodeBlock) {
        poetBuilder.addKdoc(block = block)
    }

    public override fun addKdoc(format: String, vararg args: Any) {
        poetBuilder.addKdoc(format = format, args = args)
    }

    public override fun addAnnotation(annotationSpec: AnnotationSpec) {
        poetBuilder.addAnnotation(annotationSpec = annotationSpec)
    }

    public override fun addAnnotations(annotationSpecs: Iterable<AnnotationSpec>) {
        poetBuilder.addAnnotations(annotationSpecs = annotationSpecs)
    }

    public override fun addModifiers(vararg modifiers: KModifier) {
        poetBuilder.addModifiers(modifiers = modifiers)
    }

    public override fun addModifiers(modifiers: Iterable<KModifier>) {
        poetBuilder.addModifiers(modifiers = modifiers)
    }

    public override fun jvmModifiers(modifiers: Iterable<Modifier>): Unit =
        poetBuilder.jvmModifiers(modifiers = modifiers)


    public fun addTypeVariable(typeVariable: TypeVariableName) {
        poetBuilder.addTypeVariable(typeVariable = typeVariable)
    }

    public fun addTypeVariables(typeVariables: Iterable<TypeVariableName>) {
        poetBuilder.addTypeVariables(typeVariables = typeVariables)
    }

    public fun receiver(receiverType: TypeName, kdoc: CodeBlock = emptyCodeBlock) {
        poetBuilder.receiver(receiverType = receiverType, kdoc = kdoc)
    }

    public fun receiver(receiverType: Type, kdoc: CodeBlock = emptyCodeBlock) {
        poetBuilder.receiver(receiverType = receiverType, kdoc = kdoc)
    }

    public fun receiver(
        receiverType: Type,
        kdoc: String,
        vararg args: Any,
    ) {
        poetBuilder.receiver(receiverType = receiverType, kdoc = kdoc, args = args)
    }

    public fun receiver(
        receiverType: KClass<*>,
        kdoc: CodeBlock = emptyCodeBlock,
    ) {
        poetBuilder.receiver(receiverType = receiverType, kdoc = kdoc)
    }

    public fun receiver(
        receiverType: KClass<*>,
        kdoc: String,
        vararg args: Any,
    ) {
        poetBuilder.receiver(receiverType = receiverType, kdoc = kdoc, args = args)
    }

    public fun addParameter(parameterSpec: ParameterSpec) {
        poetBuilder.addParameter(parameterSpec = parameterSpec)
    }

    public fun addParameter(
        name: String,
        type: TypeName,
        vararg modifiers: KModifier,
    ) {
        poetBuilder.addParameter(name = name, type = type, modifiers = modifiers)
    }

    public fun addParameter(
        name: String,
        type: TypeName,
        modifiers: Iterable<KModifier>,
    ) {
        poetBuilder.addParameter(name = name, type = type, modifiers = modifiers)
    }

    public fun addParameter(
        name: String,
        type: Type,
        vararg modifiers: KModifier,
    ) {
        poetBuilder.addParameter(name = name, type = type, modifiers = modifiers)
    }

    public fun addParameter(
        name: String,
        type: Type,
        modifiers: Iterable<KModifier>,
    ) {
        poetBuilder.addParameter(name = name, type = type, modifiers = modifiers)
    }

    public fun addParameter(
        name: String,
        type: KClass<*>,
        vararg modifiers: KModifier,
    ) {
        poetBuilder.addParameter(name = name, type = type, modifiers = modifiers)
    }

    public fun addParameter(
        name: String,
        type: KClass<*>,
        modifiers: Iterable<KModifier>,
    ) {
        poetBuilder.addParameter(name = name, type = type, modifiers = modifiers)
    }

    public fun addParameters(parameterSpecs: Iterable<ParameterSpec>) {
        poetBuilder.addParameters(parameterSpecs = parameterSpecs)
    }

    public fun callSuperConstructor(vararg args: CodeBlock) {
        poetBuilder.callSuperConstructor(args = args)
    }

    public fun callSuperConstructor(vararg args: String) {
        poetBuilder.callSuperConstructor(args = args)
    }

    public fun callSuperConstructor(args: Iterable<CodeBlock>) {
        poetBuilder.callSuperConstructor(args = args)
    }

    public fun callSuperConstructor(args: List<CodeBlock>) {
        poetBuilder.callSuperConstructor(args = args)
    }

    public fun callThisConstructor(vararg args: CodeBlock) {
        poetBuilder.callThisConstructor(args = args)
    }

    public fun callThisConstructor(vararg args: String) {
        poetBuilder.callThisConstructor(args = args)
    }

    public fun callThisConstructor(args: Iterable<CodeBlock>) {
        poetBuilder.callThisConstructor(args = args)
    }

    public fun callThisConstructor(args: List<CodeBlock>) {
        poetBuilder.callThisConstructor(args = args)
    }

    public fun returns(returnType: TypeName, kdoc: CodeBlock = emptyCodeBlock) {
        poetBuilder.returns(returnType = returnType, kdoc = kdoc)
    }

    public fun returns(returnType: Type, kdoc: CodeBlock = emptyCodeBlock) {
        poetBuilder.returns(returnType = returnType, kdoc = kdoc)
    }

    public fun returns(
        returnType: Type,
        kdoc: String,
        vararg args: Any,
    ) {
        poetBuilder.returns(returnType = returnType, kdoc = kdoc, args = args)
    }

    public fun returns(returnType: KClass<*>, kdoc: CodeBlock = emptyCodeBlock) {
        poetBuilder.returns(returnType = returnType, kdoc = kdoc)
    }

    public fun returns(
        returnType: KClass<*>,
        kdoc: String,
        vararg args: Any,
    ) {
        poetBuilder.returns(returnType = returnType, kdoc = kdoc, args = args)
    }


    public override fun addCode(codeBlock: CodeBlock) {
        poetBuilder.addCode(codeBlock = codeBlock)
    }

    public override fun addCode(format: String, vararg args: Any?) {
        poetBuilder.addCode(format = format, args = args)
    }

    public override fun addStatement(format: String, vararg args: Any?) {
        @Suppress("UNCHECKED_CAST")
        poetBuilder.addStatement(format, *(args as Array<Any>))
    }

    override fun addNamed(format: String, args: Map<String, *>) {
        poetBuilder.addNamedCode(format, args)
    }

    public override fun beginControlFlow(controlFlow: String, vararg args: Any) {
        poetBuilder.beginControlFlow(controlFlow = controlFlow, args = args)
    }

    public override fun nextControlFlow(controlFlow: String, vararg args: Any) {
        poetBuilder.nextControlFlow(controlFlow = controlFlow, args = args)
    }

    public override fun endControlFlow() {
        poetBuilder.endControlFlow()
    }

    public override fun clearCode() {
        poetBuilder.clearBody()
    }

    public fun addComment(format: String, vararg args: Any) {
        poetBuilder.addComment(format = format, args = args)
    }

    public fun build(): FunSpec = poetBuilder.build()
}

// -- build --

public inline fun function(name: String, config: FunSpecBuilder.() -> Unit = {}): FunSpec =
    FunSpec.builder(name = name).wrapBuilder().apply(config).build()

public inline fun constructor(config: FunSpecBuilder.() -> Unit = {}): FunSpec =
    FunSpec.constructorBuilder().wrapBuilder().apply(config).build()

public inline fun getter(config: FunSpecBuilder.() -> Unit = {}): FunSpec =
    FunSpec.getterBuilder().wrapBuilder().apply(config).build()

@Deprecated(message =
"Element APIs don't give complete information on Kotlin types. Consider using the kotlinpoet-metadata APIs instead.")
public inline fun overriding(method: ExecutableElement, config: FunSpecBuilder.() -> Unit = {}): FunSpec =
    FunSpec.overriding(method = method).wrapBuilder().apply(config).build()

@Deprecated(message =
"Element APIs don't give complete information on Kotlin types. Consider using the kotlinpoet-metadata APIs instead.")
public inline fun overriding(
    method: ExecutableElement,
    enclosing: DeclaredType,
    types: Types,
    config: FunSpecBuilder.() -> Unit = {},
): FunSpec = FunSpec.overriding(method = method, enclosing = enclosing,
    types = types).wrapBuilder().apply(config).build()

public inline fun setter(config: FunSpecBuilder.() -> Unit = {}): FunSpec =
    FunSpec.setterBuilder().wrapBuilder().apply(config).build()

// -- other --

public inline fun FunSpec.modify(name: String, config: FunSpecBuilder.() -> Unit): FunSpec =
    toBuilder(name = name).wrapBuilder().apply(config).build()
