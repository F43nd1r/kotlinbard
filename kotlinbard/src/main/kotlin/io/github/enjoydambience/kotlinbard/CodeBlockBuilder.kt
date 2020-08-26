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

import com.squareup.kotlinpoet.CodeBlock

@Suppress("FunctionName")
public fun CodeBlockBuilder(poetBuilder: CodeBlock.Builder = CodeBlock.builder()): CodeBlockBuilder =
    CodeBlockBuilder(poetBuilder, false)

@CodegenDsl
public class CodeBlockBuilder internal constructor(
    public val poetBuilder: CodeBlock.Builder,
    @Suppress("UNUSED_PARAMETER") dummy: Boolean = false,
) : CodeBuilding() {
    override fun clearCode() {
        poetBuilder.clear()
    }

    public override fun addCode(format: String, vararg args: Any?) {
        poetBuilder.add(format = format, args = args)
    }

    override fun addCode(codeBlock: CodeBlock) {
        poetBuilder.add(codeBlock)
    }

    public override fun addNamed(format: String, args: Map<String, *>) {
        poetBuilder.addNamed(format = format, arguments = args)
    }

    public override fun addStatement(format: String, vararg args: Any?) {
        poetBuilder.addStatement(format = format, args = args)
    }

    public override fun beginControlFlow(controlFlow: String, vararg args: Any) {
        poetBuilder.beginControlFlow(controlFlow = controlFlow, args = args)
    }

    public fun build(): CodeBlock = poetBuilder.build()
    public fun clear() {
        poetBuilder.clear()
    }

    public override fun endControlFlow() {
        poetBuilder.endControlFlow()
    }

    public fun indent() {
        poetBuilder.indent()
    }

    public fun isEmpty(): Boolean = poetBuilder.isEmpty()
    public fun isNotEmpty(): Boolean = poetBuilder.isNotEmpty()
    public fun nextControlFlow(controlFlow: String, vararg args: Any?) {
        poetBuilder.nextControlFlow(controlFlow = controlFlow, args = args)
    }

    public fun unindent() {
        poetBuilder.unindent()
    }
}

/**
 * Increases indent while in the given scope.
 */
public inline fun CodeBlockBuilder.indent(scope: CodeBlockBuilder.() -> Unit) {
    indent()
    this.scope()
    unindent()
}


public inline fun codeBlock(config: CodeBlockBuilder.() -> Unit = {}): CodeBlock =
    CodeBlock.builder().wrapBuilder().apply(config).build()

public fun codeBlock(format: String, vararg args: Any?): CodeBlock =
    CodeBlock.of(format, *args)

public inline fun CodeBlock.modify(config: CodeBlockBuilder.() -> Unit): CodeBlock =
    toBuilder().wrapBuilder().apply(config).build()
