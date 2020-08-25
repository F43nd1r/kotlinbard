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

import com.squareup.kotlinpoet.FunSpec

/**
 * Creates a setter with parameter, with the given [parameter name][paramName].
 */
public inline fun setter(paramName: String, config: FunSpec.Builder.() -> Unit): FunSpec =
    setter {
        addParameter(paramName, Any::class)
        config()
    }

/**
 * Adds a setter with parameter, with the given [parameter name][paramName].
 */
public inline fun PropertySpecBuilder.set(
    paramName: String,
    config: FunSpecBuilder.() -> Unit,
) {
    setter(setter(paramName, config))
}
