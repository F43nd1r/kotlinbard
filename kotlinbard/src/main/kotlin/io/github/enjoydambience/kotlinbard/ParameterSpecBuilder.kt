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
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.ParameterSpec
import javax.lang.model.element.Modifier
import kotlin.reflect.KClass

@Suppress("FunctionName")
public fun ParameterSpecBuilder(poetBuilder: ParameterSpec.Builder): ParameterSpecBuilder =
    ParameterSpecBuilder(poetBuilder, false)

@CodegenDsl
public class ParameterSpecBuilder internal constructor(
    public val poetBuilder: ParameterSpec.Builder,
    @Suppress("UNUSED_PARAMETER") dummy: Boolean,
) : Taggable.Builder<ParameterSpecBuilder> {
    public val annotations: MutableList<AnnotationSpec> get() = poetBuilder.annotations
    public val kdoc: CodeBlock.Builder get() = poetBuilder.kdoc
    public val modifiers: MutableList<KModifier> get() = poetBuilder.modifiers
    public override val tags: MutableMap<KClass<*>, Any> get() = poetBuilder.tags
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

    public fun build(): ParameterSpec = poetBuilder.build()
    public fun defaultValue(codeBlock: CodeBlock) {
        poetBuilder.defaultValue(codeBlock = codeBlock)
    }

    public fun defaultValue(format: String, vararg args: Any?) {
        poetBuilder.defaultValue(format = format, args = args)
    }

    public fun jvmModifiers(modifiers: Iterable<Modifier>) {
        poetBuilder.jvmModifiers(modifiers = modifiers)
    }
}
