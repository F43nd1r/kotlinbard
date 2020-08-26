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


@file:Suppress("DEPRECATION", "DeprecatedCallableAddReplaceWith")

package io.github.enjoydambience.kotlinbard

import com.squareup.kotlinpoet.*
import javax.lang.model.element.AnnotationMirror
import kotlin.reflect.KClass

@Suppress("FunctionName")
public fun AnnotationSpecBuilder(poetBuilder: AnnotationSpec.Builder): AnnotationSpecBuilder =
    AnnotationSpecBuilder(poetBuilder, false)

@CodegenDsl
public class AnnotationSpecBuilder internal constructor(
    public val poetBuilder: AnnotationSpec.Builder,
    @Suppress("UNUSED_PARAMETER") dummy: Boolean,
) : Taggable.Builder<AnnotationSpecBuilder> {
    public val members: MutableList<CodeBlock> get() = poetBuilder.members
    public override val tags: MutableMap<KClass<*>, Any> get() = poetBuilder.tags
    public fun addMember(codeBlock: CodeBlock) {
        poetBuilder.addMember(codeBlock = codeBlock)
    }

    public fun addMember(format: String, vararg args: Any) {
        poetBuilder.addMember(format = format, args = args)
    }

    public fun build(): AnnotationSpec = poetBuilder.build()
    public fun useSiteTarget(useSiteTarget: AnnotationSpec.UseSiteTarget?) {
        poetBuilder.useSiteTarget(useSiteTarget = useSiteTarget)
    }
}

// -- build --

public inline fun annotation(type: ClassName, config: AnnotationSpecBuilder.() -> Unit = {}):
        AnnotationSpec = AnnotationSpec.builder(type = type).wrapBuilder().apply(config).build()

public inline fun annotation(type: ParameterizedTypeName, config: AnnotationSpecBuilder.() -> Unit = {}):
        AnnotationSpec = AnnotationSpec.builder(type = type).wrapBuilder().apply(config).build()

public inline fun annotation(type: Class<out Annotation>, config: AnnotationSpecBuilder.() -> Unit = {}):
        AnnotationSpec = AnnotationSpec.builder(type = type).wrapBuilder().apply(config).build()

public inline fun annotation(type: KClass<out Annotation>, config: AnnotationSpecBuilder.() -> Unit = {}):
        AnnotationSpec = AnnotationSpec.builder(type = type).wrapBuilder().apply(config).build()

// -- get --

@Deprecated(message =
"Mirror APIs don't give complete information on Kotlin types. Consider using the kotlinpoet-metadata APIs instead.")
public fun getAnnotation(annotation: AnnotationMirror): AnnotationSpec =
    AnnotationSpec.get(annotation = annotation)

public fun getAnnotation(annotation: Annotation, includeDefaultValues: Boolean): AnnotationSpec =
    AnnotationSpec.get(annotation = annotation, includeDefaultValues = includeDefaultValues)

// -- other --

public inline fun AnnotationSpec.modify(config: AnnotationSpecBuilder.() -> Unit): AnnotationSpec =
    toBuilder().wrapBuilder().apply(config).build()
