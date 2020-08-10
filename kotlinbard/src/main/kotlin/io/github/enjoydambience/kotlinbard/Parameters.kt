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

@file:Suppress("FunctionName")

package io.github.enjoydambience.kotlinbard

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import java.lang.reflect.Type
import kotlin.reflect.KClass


/**
 * Creates a scope for adding parameters to this function.
 *
 * Use [of][FunParametersBuilder.of] to add new parameter, [init][FunParametersBuilder.init]
 * to set default value of that parameter, and [invoke][FunParametersBuilder.invoke]
 * for further configuration.
 *
 * You can also [add][FunParametersBuilder.add] a ParameterSpec to add it directly, and optionally [invoke][FunParametersBuilder.invoke]
 * for further modification.
 *
 * Examples:
 * ```kotlin
 * parameters {
 *     "one" of Int::class init 3.literal
 *     ("foo" of myType) { addModifiers(VARARG) }
 *     "bar" of String::class init cb("%S", "hello"))
 * }
 * ```
 */
public inline fun FunSpecBuilder.params(config: FunParametersBuilder.() -> Unit) {
    FunParametersBuilder().apply(config).addTo(this)
}

@CodegenDsl
public abstract class BaseParametersBuilder internal constructor() {
    protected val parameters: MutableList<Parameter> = mutableListOf()
    public infix fun String.of(type: TypeName): Parameter {
        return Parameter(this, type)
            .also { parameters += it }
    }

    public infix fun String.of(type: KClass<*>): Parameter = of(type.asTypeName())
    public infix fun String.of(type: Type): Parameter = of(type.asTypeName())

    public inline fun <reified T> String.of(): Parameter = of(T::class)

    public infix fun Parameter.init(codeBlock: CodeBlock): Parameter = apply {
        poetBuilder.defaultValue(codeBlock)
    }

    public fun Parameter.init(format: String, vararg args: Any): Parameter = apply {
        poetBuilder.defaultValue(format, args)
    }

    public inline operator fun Parameter.invoke(config: ParameterSpecBuilder.() -> Unit) {
        poetBuilder.apply(config)
    }

    public open class Parameter internal constructor(
        @PublishedApi internal val poetBuilder: ParameterSpecBuilder
    ) {
        internal constructor(name: String, type: TypeName) : this(ParameterSpec.builder(name, type))
    }

    public fun add(parameter: ParameterSpec): Parameter {
        return Parameter(parameter.toBuilder())
    }
}

public class FunParametersBuilder : BaseParametersBuilder() {
    @PublishedApi
    internal fun addTo(builder: FunSpecBuilder) {
        parameters.forEach { p ->
            builder.addParameter(p.poetBuilder.build())
        }
    }
}
