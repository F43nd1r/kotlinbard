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

@file:Suppress(
    "NO_EXPLICIT_VISIBILITY_IN_API_MODE_WARNING",
    "unused",
    "DEPRECATION",
    "DeprecatedCallableAddReplaceWith"
)

package io.github.enjoydambience.kotlinbard

import com.squareup.kotlinpoet.*
import java.lang.reflect.Type
import javax.lang.model.element.ExecutableElement
import javax.lang.model.type.DeclaredType
import javax.lang.model.util.Types
import kotlin.reflect.KClass

@Deprecated(
    "use file instead",
    ReplaceWith("file(packageName=packageName, fileName=fileName, config=config)")
)
inline fun buildFile(
    packageName: String,
    fileName: String,
    config: FileSpecBuilder.() -> Unit = {},
): FileSpec = file(packageName = packageName, fileName = fileName, config = config)

@Deprecated(
    "use annotationClass instead",
    ReplaceWith("annotationClass(className=className, config=config)")
)
inline fun buildAnnotationClass(className: ClassName, config: TypeSpecBuilder.() -> Unit = {}):
        TypeSpec = annotationClass(className = className, config = config)

@Deprecated(
    "use annotationClass instead",
    ReplaceWith("annotationClass(name=name, config=config)")
)
inline fun buildAnnotationClass(name: String, config: TypeSpecBuilder.() -> Unit = {}): TypeSpec =
    annotationClass(name = name, config = config)

@Deprecated(
    "use anonymousClass instead",
    ReplaceWith("anonymousClass(config=config)")
)
inline fun buildAnonymousClass(config: TypeSpecBuilder.() -> Unit = {}): TypeSpec =
    anonymousClass(config = config)

@Deprecated(
    "use class instead",
    ReplaceWith("`class`(className=className, config=config)")
)
inline fun buildClass(className: ClassName, config: TypeSpecBuilder.() -> Unit = {}): TypeSpec =
    `class`(className = className, config = config)

@Deprecated(
    "use class instead",
    ReplaceWith("`class`(name=name, config=config)")
)
inline fun buildClass(name: String, config: TypeSpecBuilder.() -> Unit = {}): TypeSpec =
    `class`(name = name, config = config)

@Deprecated(
    "use companionObject instead",
    ReplaceWith("companionObject(name=name, config=config)")
)
inline fun buildCompanionObject(name: String?, config: TypeSpecBuilder.() -> Unit = {}): TypeSpec =
    companionObject(name = name, config = config)

@Deprecated(
    "use enumClass instead",
    ReplaceWith("enumClass(className=className, config=config)")
)
inline fun buildEnum(className: ClassName, config: TypeSpecBuilder.() -> Unit = {}): TypeSpec =
    enumClass(className = className, config = config)

@Deprecated(
    "use enumClass instead",
    ReplaceWith("enumClass(name=name, config=config)")
)
inline fun buildEnum(name: String, config: TypeSpecBuilder.() -> Unit = {}): TypeSpec =
    enumClass(name = name, config = config)

@Deprecated(
    "use expectClass instead",
    ReplaceWith("expectClass(className=className, config=config)")
)
inline fun buildExpectClass(className: ClassName, config: TypeSpecBuilder.() -> Unit = {}): TypeSpec =
    expectClass(className = className, config = config)

@Deprecated(
    "use expectClass instead",
    ReplaceWith("expectClass(name=name, config=config)")
)
inline fun buildExpectClass(name: String, config: TypeSpecBuilder.() -> Unit = {}): TypeSpec =
    expectClass(name = name, config = config)

@Deprecated(
    "use funInterface instead",
    ReplaceWith("funInterface(className=className, config=config)")
)
inline fun buildFunInterface(className: ClassName, config: TypeSpecBuilder.() -> Unit = {}):
        TypeSpec = funInterface(className = className, config = config)

@Deprecated(
    "use funInterface instead",
    ReplaceWith("funInterface(name=name, config=config)")
)
inline fun buildFunInterface(name: String, config: TypeSpecBuilder.() -> Unit = {}): TypeSpec =
    funInterface(name = name, config = config)

@Deprecated(
    "use interface instead",
    ReplaceWith("`interface`(className=className, config=config)")
)
inline fun buildInterface(className: ClassName, config: TypeSpecBuilder.() -> Unit = {}): TypeSpec =
    `interface`(className = className, config = config)

@Deprecated(
    "use interface instead",
    ReplaceWith("`interface`(name=name, config=config)")
)
inline fun buildInterface(name: String, config: TypeSpecBuilder.() -> Unit = {}): TypeSpec =
    `interface`(name = name, config = config)

@Deprecated(
    "use object instead",
    ReplaceWith("`object`(className=className, config=config)")
)
inline fun buildObject(className: ClassName, config: TypeSpecBuilder.() -> Unit = {}): TypeSpec =
    `object`(className = className, config = config)

@Deprecated(
    "use object instead",
    ReplaceWith("`object`(name=name, config=config)")
)
inline fun buildObject(name: String, config: TypeSpecBuilder.() -> Unit = {}): TypeSpec =
    `object`(name = name, config = config)

@Deprecated(
    "use property instead",
    ReplaceWith("property(name=name, type=type, modifiers=modifiers, config=config)")
)
inline fun buildProperty(
    name: String,
    type: TypeName,
    vararg modifiers: KModifier,
    config: PropertySpecBuilder.() -> Unit = {},
): PropertySpec = property(name = name, type = type, modifiers = modifiers, config = config)

@Deprecated(
    "use property instead",
    ReplaceWith("property(name=name, type=type, modifiers=modifiers, config=config)")
)
inline fun buildProperty(
    name: String,
    type: TypeName,
    modifiers: Iterable<KModifier>,
    config: PropertySpecBuilder.() -> Unit = {},
): PropertySpec = property(name = name, type = type, modifiers = modifiers, config = config)

@Deprecated(
    "use property instead",
    ReplaceWith("property(name=name, type=type, modifiers=modifiers, config=config)")
)
inline fun buildProperty(
    name: String,
    type: Type,
    vararg modifiers: KModifier,
    config: PropertySpecBuilder.() -> Unit = {},
): PropertySpec = property(name = name, type = type, modifiers = modifiers, config = config)

@Deprecated(
    "use property instead",
    ReplaceWith("property(name=name, type=type, modifiers=modifiers, config=config)")
)
inline fun buildProperty(
    name: String,
    type: Type,
    modifiers: Iterable<KModifier>,
    config: PropertySpecBuilder.() -> Unit = {},
): PropertySpec = property(name = name, type = type, modifiers = modifiers, config = config)

@Deprecated(
    "use property instead",
    ReplaceWith("property(name=name, type=type, modifiers=modifiers, config=config)")
)
inline fun buildProperty(
    name: String,
    type: KClass<*>,
    vararg modifiers: KModifier,
    config: PropertySpecBuilder.() -> Unit = {},
): PropertySpec = property(name = name, type = type, modifiers = modifiers, config = config)

@Deprecated(
    "use property instead",
    ReplaceWith("property(name=name, type=type, modifiers=modifiers, config=config)")
)
inline fun buildProperty(
    name: String,
    type: KClass<*>,
    modifiers: Iterable<KModifier>,
    config: PropertySpecBuilder.() -> Unit = {},
): PropertySpec = property(name = name, type = type, modifiers = modifiers, config = config)

@Deprecated(
    "use function instead",
    ReplaceWith("function(name=name, config=config)")
)
inline fun buildFunction(name: String, config: FunSpecBuilder.() -> Unit = {}): FunSpec =
    function(name = name, config = config)

@Deprecated(
    "use constructor instead",
    ReplaceWith("constructor(config=config)")
)
inline fun buildConstructor(config: FunSpecBuilder.() -> Unit = {}): FunSpec =
    constructor(config = config)

@Deprecated(
    "use getter instead",
    ReplaceWith("getter(config=config)")
)
inline fun buildGetter(config: FunSpecBuilder.() -> Unit = {}): FunSpec = getter(config = config)

@Deprecated(
    "use overriding instead",
    ReplaceWith("overriding(method=method, config=config)")
)
inline fun buildOverriding(method: ExecutableElement, config: FunSpecBuilder.() -> Unit = {}):
        FunSpec = overriding(method = method, config = config)

@Deprecated(
    "use overriding instead",
    ReplaceWith("overriding(method=method, enclosing=enclosing, types=types, config=config)")
)
inline fun buildOverriding(
    method: ExecutableElement,
    enclosing: DeclaredType,
    types: Types,
    config: FunSpecBuilder.() -> Unit = {},
): FunSpec = overriding(method = method, enclosing = enclosing, types = types, config = config)

@Deprecated(
    "use setter instead",
    ReplaceWith("setter(config=config)")
)
inline fun buildSetter(config: FunSpecBuilder.() -> Unit = {}): FunSpec = setter(config = config)

@Deprecated(
    "use parameter instead",
    ReplaceWith("parameter(name=name, type=type, modifiers=modifiers, config=config)")
)
inline fun buildParameter(
    name: String,
    type: TypeName,
    vararg modifiers: KModifier,
    config: ParameterSpecBuilder.() -> Unit = {},
): ParameterSpec = parameter(name = name, type = type, modifiers = modifiers, config = config)

@Deprecated(
    "use parameter instead",
    ReplaceWith("parameter(name=name, type=type, modifiers=modifiers, config=config)")
)
inline fun buildParameter(
    name: String,
    type: TypeName,
    modifiers: Iterable<KModifier>,
    config: ParameterSpecBuilder.() -> Unit = {},
): ParameterSpec = parameter(name = name, type = type, modifiers = modifiers, config = config)

@Deprecated(
    "use parameter instead",
    ReplaceWith("parameter(name=name, type=type, modifiers=modifiers, config=config)")
)
inline fun buildParameter(
    name: String,
    type: Type,
    vararg modifiers: KModifier,
    config: ParameterSpecBuilder.() -> Unit = {},
): ParameterSpec = parameter(name = name, type = type, modifiers = modifiers, config = config)

@Deprecated(
    "use parameter instead",
    ReplaceWith("parameter(name=name, type=type, modifiers=modifiers, config=config)")
)
inline fun buildParameter(
    name: String,
    type: Type,
    modifiers: Iterable<KModifier>,
    config: ParameterSpecBuilder.() -> Unit = {},
): ParameterSpec = parameter(name = name, type = type, modifiers = modifiers, config = config)

@Deprecated(
    "use parameter instead",
    ReplaceWith("parameter(name=name, type=type, modifiers=modifiers, config=config)")
)
inline fun buildParameter(
    name: String,
    type: KClass<*>,
    vararg modifiers: KModifier,
    config: ParameterSpecBuilder.() -> Unit = {},
): ParameterSpec = parameter(name = name, type = type, modifiers = modifiers, config = config)

@Deprecated(
    "use parameter instead",
    ReplaceWith("parameter(name=name, type=type, modifiers=modifiers, config=config)")
)
inline fun buildParameter(
    name: String,
    type: KClass<*>,
    modifiers: Iterable<KModifier>,
    config: ParameterSpecBuilder.() -> Unit = {},
): ParameterSpec = parameter(name = name, type = type, modifiers = modifiers, config = config)

@Deprecated(
    "use typeAlias instead",
    ReplaceWith("typeAlias(name=name, type=type, config=config)")
)
inline fun buildTypeAlias(
    name: String,
    type: TypeName,
    config: TypeAliasSpecBuilder.() -> Unit = {},
): TypeAliasSpec = typeAlias(name = name, type = type, config = config)

@Deprecated(
    "use typeAlias instead",
    ReplaceWith("typeAlias(name=name, type=type, config=config)")
)
inline fun buildTypeAlias(
    name: String,
    type: Type,
    config: TypeAliasSpecBuilder.() -> Unit = {},
): TypeAliasSpec = typeAlias(name = name, type = type, config = config)

@Deprecated(
    "use typeAlias instead",
    ReplaceWith("typeAlias(name=name, type=type, config=config)")
)
inline fun buildTypeAlias(
    name: String,
    type: KClass<*>,
    config: TypeAliasSpecBuilder.() -> Unit = {},
): TypeAliasSpec = typeAlias(name = name, type = type, config = config)

@Deprecated(
    "use annotation instead",
    ReplaceWith("annotation(type=type, config=config)")
)
inline fun buildAnnotation(type: ClassName, config: AnnotationSpecBuilder.() -> Unit = {}):
        AnnotationSpec = annotation(type = type, config = config)

@Deprecated(
    "use annotation instead",
    ReplaceWith("annotation(type=type, config=config)")
)
inline fun buildAnnotation(
    type: ParameterizedTypeName,
    config: AnnotationSpecBuilder.() -> Unit =
        {},
): AnnotationSpec = annotation(type = type, config = config)

@Deprecated(
    "use annotation instead",
    ReplaceWith("annotation(type=type, config=config)")
)
inline fun buildAnnotation(
    type: Class<out Annotation>,
    config: AnnotationSpecBuilder.() -> Unit =
        {},
): AnnotationSpec = annotation(type = type, config = config)

@Deprecated(
    "use annotation instead",
    ReplaceWith("annotation(type=type, config=config)")
)
inline fun buildAnnotation(
    type: KClass<out Annotation>,
    config: AnnotationSpecBuilder.() -> Unit =
        {},
): AnnotationSpec = annotation(type = type, config = config)

@Deprecated(
    "use codeBlock instead",
    ReplaceWith("codeBlock(config=config)")
)
inline fun buildCodeBlock(config: CodeBlockBuilder.() -> Unit = {}): CodeBlock =
    codeBlock(config = config)
