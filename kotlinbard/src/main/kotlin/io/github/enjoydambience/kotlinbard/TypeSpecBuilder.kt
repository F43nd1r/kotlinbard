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
public fun TypeSpecBuilder(poetBuilder: TypeSpec.Builder): TypeSpecBuilder =
    TypeSpecBuilder(poetBuilder, false)

@CodegenDsl
public class TypeSpecBuilder internal constructor(
    public val poetBuilder: TypeSpec.Builder,
    @Suppress("UNUSED_PARAMETER") dummy: Boolean,
) : WithAnnotationsBuilder,
    WithModifiersBuilder,
    WithMembersBuilder,
    Taggable.Builder<TypeSpecBuilder>,
    OriginatingElementsHolder.Builder<TypeSpecBuilder> {
    public override val annotations: MutableList<AnnotationSpec> get() = poetBuilder.annotationSpecs
    public val enumConstants: MutableMap<String, TypeSpec> get() = poetBuilder.enumConstants
    public val funSpecs: MutableList<FunSpec> get() = poetBuilder.funSpecs
    public var initializerIndex: Int
        get() = poetBuilder.initializerIndex
        public set(value) {
            poetBuilder.initializerIndex = value
        }
    public override val modifiers: MutableSet<KModifier> get() = poetBuilder.modifiers
    override val originatingElements: MutableList<Element> get() = poetBuilder.originatingElements
    public val propertySpecs: MutableList<PropertySpec> get() = poetBuilder.propertySpecs
    public val superclassConstructorParameters: MutableList<CodeBlock> get() = poetBuilder.superclassConstructorParameters
    public val superinterfaces: MutableMap<TypeName, CodeBlock?> get() = poetBuilder.superinterfaces
    public override val tags: MutableMap<KClass<*>, Any> get() = poetBuilder.tags
    public val typeSpecs: MutableList<TypeSpec> get() = poetBuilder.typeSpecs
    public val typeVariables: MutableList<TypeVariableName> get() = poetBuilder.typeVariables

    public override fun addAnnotation(annotationSpec: AnnotationSpec) {
        poetBuilder.addAnnotation(annotationSpec = annotationSpec)
    }

    public override fun addAnnotations(annotationSpecs: Iterable<AnnotationSpec>) {
        poetBuilder.addAnnotations(annotationSpecs = annotationSpecs)
    }

    public fun addEnumConstant(name: String, typeSpec: TypeSpec = anonymousClass()) {
        poetBuilder.addEnumConstant(name = name, typeSpec = typeSpec)
    }

    public fun addInitializerBlock(block: CodeBlock) {
        poetBuilder.addInitializerBlock(block = block)
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


    public override fun addFunction(funSpec: FunSpec) {
        poetBuilder.addFunction(funSpec = funSpec)
    }

    public override fun addFunctions(funSpecs: Iterable<FunSpec>) {
        poetBuilder.addFunctions(funSpecs = funSpecs)
    }

    public override fun addProperty(propertySpec: PropertySpec) {
        poetBuilder.addProperty(propertySpec = propertySpec)
    }

    public override fun addProperties(propertySpecs: Iterable<PropertySpec>) {
        poetBuilder.addProperties(propertySpecs = propertySpecs)
    }

    public override fun addType(typeSpec: TypeSpec) {
        poetBuilder.addType(typeSpec = typeSpec)
    }

    public override fun addTypes(typeSpecs: Iterable<TypeSpec>) {
        poetBuilder.addTypes(typeSpecs = typeSpecs)
    }

    public fun addSuperclassConstructorParameter(codeBlock: CodeBlock) {
        poetBuilder.addSuperclassConstructorParameter(codeBlock = codeBlock)
    }

    public fun addSuperclassConstructorParameter(format: String, vararg args: Any) {
        poetBuilder.addSuperclassConstructorParameter(format = format, args = args)
    }

    public fun addSuperinterface(
        superinterface: TypeName,
        delegate: CodeBlock = emptyCodeBlock,
    ) {
        poetBuilder.addSuperinterface(superinterface = superinterface, delegate = delegate)
    }

    public fun addSuperinterface(superinterface: TypeName, constructorParameter: String) {
        poetBuilder.addSuperinterface(superinterface = superinterface,
            constructorParameter = constructorParameter)
    }

    public fun addSuperinterface(
        superinterface: Type,
        delegate: CodeBlock = emptyCodeBlock,
    ) {
        poetBuilder.addSuperinterface(superinterface = superinterface, delegate = delegate)
    }

    public fun addSuperinterface(
        superinterface: KClass<*>,
        delegate: CodeBlock = emptyCodeBlock,
    ) {
        poetBuilder.addSuperinterface(superinterface = superinterface, delegate = delegate)
    }

    public fun addSuperinterface(superinterface: KClass<*>, constructorParameterName: String) {
        poetBuilder.addSuperinterface(superinterface = superinterface,
            constructorParameterName = constructorParameterName)
    }

    public fun addSuperinterfaces(superinterfaces: Iterable<TypeName>) {
        poetBuilder.addSuperinterfaces(superinterfaces = superinterfaces)
    }

    public fun addTypeVariable(typeVariable: TypeVariableName) {
        poetBuilder.addTypeVariable(typeVariable = typeVariable)
    }

    public fun addTypeVariables(typeVariables: Iterable<TypeVariableName>) {
        poetBuilder.addTypeVariables(typeVariables = typeVariables)
    }

    public fun build(): TypeSpec = poetBuilder.build()
    public fun primaryConstructor(primaryConstructor: FunSpec?) {
        poetBuilder.primaryConstructor(primaryConstructor = primaryConstructor)
    }

    public fun superclass(superclass: TypeName) {
        poetBuilder.superclass(superclass = superclass)
    }

    public fun superclass(superclass: Type) {
        poetBuilder.superclass(superclass = superclass)
    }

    public fun superclass(superclass: KClass<*>) {
        poetBuilder.superclass(superclass = superclass)
    }
}

// -- build --

public inline fun annotationClass(className: ClassName, config: TypeSpecBuilder.() -> Unit = {}): TypeSpec =
    TypeSpec.annotationBuilder(className = className).wrapBuilder().apply(config).build()

public inline fun annotationClass(name: String, config: TypeSpecBuilder.() -> Unit = {}): TypeSpec =
    TypeSpec.annotationBuilder(name = name).wrapBuilder().apply(config).build()

public inline fun anonymousClass(config: TypeSpecBuilder.() -> Unit = {}): TypeSpec =
    TypeSpec.anonymousClassBuilder().wrapBuilder().apply(config).build()

public inline fun `class`(className: ClassName, config: TypeSpecBuilder.() -> Unit = {}): TypeSpec =
    TypeSpec.classBuilder(className = className).wrapBuilder().apply(config).build()

public inline fun `class`(name: String, config: TypeSpecBuilder.() -> Unit = {}): TypeSpec =
    TypeSpec.classBuilder(name = name).wrapBuilder().apply(config).build()

public inline fun companionObject(name: String?, config: TypeSpecBuilder.() -> Unit = {}): TypeSpec =
    TypeSpec.companionObjectBuilder(name = name).wrapBuilder().apply(config).build()

public inline fun enumClass(className: ClassName, config: TypeSpecBuilder.() -> Unit = {}): TypeSpec =
    TypeSpec.enumBuilder(className = className).wrapBuilder().apply(config).build()

public inline fun enumClass(name: String, config: TypeSpecBuilder.() -> Unit = {}): TypeSpec =
    TypeSpec.enumBuilder(name = name).wrapBuilder().apply(config).build()

public inline fun expectClass(className: ClassName, config: TypeSpecBuilder.() -> Unit = {}): TypeSpec =
    TypeSpec.expectClassBuilder(className = className).wrapBuilder().apply(config).build()

public inline fun expectClass(name: String, config: TypeSpecBuilder.() -> Unit = {}): TypeSpec =
    TypeSpec.expectClassBuilder(name = name).wrapBuilder().apply(config).build()

public inline fun funInterface(className: ClassName, config: TypeSpecBuilder.() -> Unit = {}): TypeSpec =
    TypeSpec.funInterfaceBuilder(className = className).wrapBuilder().apply(config).build()

public inline fun funInterface(name: String, config: TypeSpecBuilder.() -> Unit = {}): TypeSpec =
    TypeSpec.funInterfaceBuilder(name = name).wrapBuilder().apply(config).build()

public inline fun `interface`(className: ClassName, config: TypeSpecBuilder.() -> Unit = {}): TypeSpec =
    TypeSpec.interfaceBuilder(className = className).wrapBuilder().apply(config).build()

public inline fun `interface`(name: String, config: TypeSpecBuilder.() -> Unit = {}): TypeSpec =
    TypeSpec.interfaceBuilder(name = name).wrapBuilder().apply(config).build()

public inline fun `object`(className: ClassName, config: TypeSpecBuilder.() -> Unit = {}): TypeSpec =
    TypeSpec.objectBuilder(className = className).wrapBuilder().apply(config).build()

public inline fun `object`(name: String, config: TypeSpecBuilder.() -> Unit = {}): TypeSpec =
    TypeSpec.objectBuilder(name = name).wrapBuilder().apply(config).build()

// -- other --

public inline fun TypeSpec.modify(
    kind: TypeSpec.Kind = this.kind,
    name: String? = this.name,
    config: TypeSpecBuilder.() -> Unit,
): TypeSpec = toBuilder(kind = kind, name = name).wrapBuilder().apply(config).build()
