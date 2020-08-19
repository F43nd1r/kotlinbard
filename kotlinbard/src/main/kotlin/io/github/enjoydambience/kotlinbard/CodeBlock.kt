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

import com.squareup.kotlinpoet.CodeBlock.Builder


@CodegenDsl
public class CodeBlockBuilder internal constructor(
    public val poetType: Builder
) : CodeBuildingScope() {
    override fun clearCode() {
        poetType.clear()
    }

    override fun addCode(codeBlock: CodeBlock) {
        poetType.add(codeBlock)
    }

    override fun addCode(format: String, vararg args: Any?) {
        poetType.add(format, *args)
    }

    override fun addNamed(format: String, args: Map<String, *>) {
        poetType.addNamed(format = format, arguments = args)
    }

    public override fun addStatement(format: String, vararg args: Any?) {
        poetType.addStatement(format = format, args = *args)
    }

    override fun indent() {
        poetType.indent()
    }

    override fun unindent() {
        poetType.unindent()
    }

    override fun beginControlFlow(controlFlow: String, vararg args: Any?) {
        poetType.beginControlFlow(controlFlow = controlFlow, args = *args)
    }

    override fun endControlFlow() {
        poetType.endControlFlow()
    }


//    public override fun nextControlFlow(controlFlow: String, vararg args: Any?) {
//        poetType.nextControlFlow(controlFlow = controlFlow, args = *args)
//    }

    public fun isEmpty(): Boolean = poetType.isEmpty()
    public fun isNotEmpty(): Boolean = poetType.isNotEmpty()

    public fun build(): CodeBlock = poetType.build()
}

/**
 * Creates a new [CodeBlockBuilder].
 */
@Suppress("FunctionName")
public fun CodeBlockBuilder(): CodeBlockBuilder = CodeBlockBuilder(CodeBlock.builder())
