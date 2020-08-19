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

@CodegenDsl
public abstract class CodeBuildingScope internal constructor() {
    public abstract fun clearCode()

    public abstract fun addCode(codeBlock: CodeBlock)
    public abstract fun addCode(format: String, vararg args: Any?)
    public fun addStatement(codeBlock: CodeBlock): Unit = addStatement("%L", codeBlock)
    public abstract fun addStatement(format: String, vararg args: Any?)

    public abstract fun addNamed(format: String, args: Map<String, *>)

    public abstract fun indent()
    public abstract fun unindent()

    public abstract fun beginControlFlow(controlFlow: String, vararg args: Any?)

    //    public abstract fun nextControlFlow(controlFlow: String, vararg args: Any?)
    public abstract fun endControlFlow()

    /**
     * Used to quickly create a codeblock.
     */
    public fun String.fmt(vararg args: Any?): CodeBlock = CodeBlock.of(this, *args)

    /**
     * Adds a code block.
     */
    public operator fun CodeBlock.unaryPlus(): Unit = addCode(this)

    /**
     * Adds a string as an unformatted code block.
     */
    public operator fun String.unaryPlus(): Unit = addCode(this)

    /**
     * Adds a code block as a _statement_.
     */
    public operator fun CodeBlock.unaryMinus(): Unit = addStatement(this)

    /**
     * Adds a string as an unformatted _statement_.
     */
    public operator fun String.unaryMinus(): Unit = addStatement(this)

    /**
     * Short for [controlFlow], using an unformatted string.
     */
    public inline operator fun String.invoke(body: CodeBuildingScope.() -> Unit): Unit =
        controlFlow(this, body = body)

    /**
     * Short for control flow using formatted string: [fmt] and [controlFlow] combined.
     */
    public inline fun String.fmt(vararg args: Any, body: CodeBuildingScope.() -> Unit): Unit =
        controlFlow(this, *args, body = body)
}

/**
 * Increases indent while in the given scope.
 */
public inline fun CodeBuildingScope.indent(scope: CodeBuildingScope.() -> Unit) {
    indent()
    this.scope()
    unindent()
}

/**
 * Creates a control flow, wrapped around the current scope.
 */
public inline fun CodeBuildingScope.controlFlow(
    controlFlow: String,
    vararg args: Any,
    body: CodeBuildingScope.() -> Unit
) {
    beginControlFlow(controlFlow, *args)
    this.body()
    endControlFlow()
}

