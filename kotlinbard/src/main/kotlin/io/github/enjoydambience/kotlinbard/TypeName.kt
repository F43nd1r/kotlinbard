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
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeVariableName
import kotlin.reflect.KClass

/**
 * Returns this TypeName, nullable.
 */
@Suppress("UNCHECKED_CAST")
public val <T : TypeName> T.nullable: T
    get() = copy(nullable = true) as T

/**
 * Returns this TypeName, plus the given additional [annotations].
 */
@Suppress("UNCHECKED_CAST")
public fun <T : TypeName> T.plusAnnotations(vararg annotations: AnnotationSpec): T {
    return copy(annotations = this.annotations + annotations) as T
}

/**
 * Returns this TypeName, replacing annotations with the given [annotations].
 */
@Suppress("UNCHECKED_CAST")
public fun <T : TypeName> T.setAnnotations(vararg annotations: AnnotationSpec): T {
    return copy(annotations = annotations.toList()) as T
}

/**
 * Returns this TypeName with an given tag.
 *
 * If the [tag] is null, the tag will be removed
 */
@Suppress("UNCHECKED_CAST")
public fun <T : TypeName> T.plusTag(type: KClass<*>, tag: Any?): T {
    val newTags = if (tag == null) {
        this.tags - type
    } else {
        this.tags + (type to tag)
    }
    return copy(tags = newTags) as T
}

/**
 * Returns this LambdaTypeName, but suspending.
 */
public val LambdaTypeName.suspend: LambdaTypeName get() = copy(suspending = true)

/**
 * Returns this TypeVariable, but reified.
 */
public val TypeVariableName.reified: TypeVariableName get() = copy(reified = true)

/**
 * Returns this TypeVariableName, plus the given additional [bounds]
 */
public fun TypeVariableName.plusBounds(vararg bounds: TypeName): TypeVariableName {
    return copy(bounds = this.bounds + bounds)
}

/**
 * Returns this TypeVariableName, plus the given additional [bounds]
 */
public fun TypeVariableName.setBounds(vararg bounds: TypeName): TypeVariableName {
    return copy(bounds = bounds.toList())
}
