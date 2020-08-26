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
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeAliasSpec
import com.squareup.kotlinpoet.TypeSpec
import kotlin.reflect.KClass

@Suppress("FunctionName")
public fun FileSpecBuilder(poetBuilder: FileSpec.Builder): FileSpecBuilder =
    FileSpecBuilder(poetBuilder, false)

@CodegenDsl
public class FileSpecBuilder internal constructor(
    public val poetBuilder: FileSpec.Builder,
    @Suppress("UNUSED_PARAMETER") dummy: Boolean,
) : Taggable.Builder<FileSpecBuilder> {
    public val annotations: MutableList<AnnotationSpec> get() = poetBuilder.annotations
    public val imports: List<Import> get() = poetBuilder.imports
    public val members: MutableList<Any> get() = poetBuilder.members
    public val name: String get() = poetBuilder.name
    public val packageName: String get() = poetBuilder.packageName
    public override val tags: MutableMap<KClass<*>, Any> get() = poetBuilder.tags
    public fun addAliasedImport(className: ClassName, `as`: String) {
        poetBuilder.addAliasedImport(className = className, `as` = `as`)
    }

    public fun addAliasedImport(
        className: ClassName,
        memberName: String,
        `as`: String,
    ) {
        poetBuilder.addAliasedImport(className = className, memberName = memberName, `as` = `as`)
    }

    public fun addAliasedImport(memberName: MemberName, `as`: String) {
        poetBuilder.addAliasedImport(memberName = memberName, `as` = `as`)
    }

    public fun addAliasedImport(`class`: Class<*>, `as`: String) {
        poetBuilder.addAliasedImport(`class` = `class`, `as` = `as`)
    }

    public fun addAliasedImport(`class`: KClass<*>, `as`: String) {
        poetBuilder.addAliasedImport(`class` = `class`, `as` = `as`)
    }

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

    public fun addComment(format: String, vararg args: Any) {
        poetBuilder.addComment(format = format, args = args)
    }

    public fun addFunction(funSpec: FunSpec) {
        poetBuilder.addFunction(funSpec = funSpec)
    }

    public fun addImport(className: ClassName, vararg names: String) {
        poetBuilder.addImport(className = className, names = names)
    }

    public fun addImport(className: ClassName, names: Iterable<String>) {
        poetBuilder.addImport(className = className, names = names)
    }

    public fun addImport(import: Import) {
        poetBuilder.addImport(import = import)
    }

    public fun addImport(`class`: Class<*>, vararg names: String) {
        poetBuilder.addImport(`class` = `class`, names = names)
    }

    public fun addImport(`class`: Class<*>, names: Iterable<String>) {
        poetBuilder.addImport(`class` = `class`, names = names)
    }

    public fun addImport(constant: Enum<*>) {
        poetBuilder.addImport(constant = constant)
    }

    public fun addImport(packageName: String, vararg names: String) {
        poetBuilder.addImport(packageName = packageName, names = names)
    }

    public fun addImport(packageName: String, names: Iterable<String>) {
        poetBuilder.addImport(packageName = packageName, names = names)
    }

    public fun addImport(`class`: KClass<*>, vararg names: String) {
        poetBuilder.addImport(`class` = `class`, names = names)
    }

    public fun addImport(`class`: KClass<*>, names: Iterable<String>) {
        poetBuilder.addImport(`class` = `class`, names = names)
    }

    public fun addProperty(propertySpec: PropertySpec) {
        poetBuilder.addProperty(propertySpec = propertySpec)
    }

    public fun addType(typeSpec: TypeSpec) {
        poetBuilder.addType(typeSpec = typeSpec)
    }

    public fun addTypeAlias(typeAliasSpec: TypeAliasSpec) {
        poetBuilder.addTypeAlias(typeAliasSpec = typeAliasSpec)
    }

    public fun build(): FileSpec = poetBuilder.build()
    public fun clearComment() {
        poetBuilder.clearComment()
    }

    public fun clearImports() {
        poetBuilder.clearImports()
    }

    public fun indent(indent: String) {
        poetBuilder.indent(indent = indent)
    }
}
