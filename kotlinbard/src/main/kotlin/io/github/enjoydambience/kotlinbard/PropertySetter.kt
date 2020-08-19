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

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.PropertySpec

@PublishedApi
internal val anySetterParam = ClassName("", "SetterParam")
/**
 * Creates a setter for this property.
 *
 * This will also add the setter parameter with the given [paramName].
 */
public inline fun PropertySpec.Builder.set(
    paramName: String = "value",
    config: FunSpec.Builder.() -> Unit
): PropertySpec.Builder = setter(setter {
    addParameter(paramName, anySetterParam)
    config()
})

/**
 * Creates a setter for this property with no parameter (and should have no body).
 */
public inline fun PropertySpec.Builder.set(
    config: FunSpec.Builder.() -> Unit
): PropertySpec.Builder =
    setter(setter(config = config))
