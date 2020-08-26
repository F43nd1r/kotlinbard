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

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.asClassName
import kotlin.reflect.KClass


/**
 * A builder of an element that can have [annotations][AnnotationSpec].
 */
public interface WithAnnotationsBuilder {
    public val annotations: MutableList<AnnotationSpec>
    public fun addAnnotation(annotationSpec: AnnotationSpec) {
        annotations += annotationSpec
    }

    public fun addAnnotations(annotationSpecs: Iterable<AnnotationSpec>) {
        annotationSpecs.forEach { addAnnotation(it) }
    }

    public fun addAnnotation(annotation: ClassName): Unit = addAnnotation(annotation(annotation))

    public fun addAnnotation(annotation: KClass<*>): Unit = addAnnotation(annotation.asClassName())

    public fun addAnnotation(annotation: Class<*>): Unit = addAnnotation(annotation.asClassName())
}

public inline fun <reified T : Annotation> WithAnnotationsBuilder.addAnnotation(): Unit = addAnnotation(T::class)
