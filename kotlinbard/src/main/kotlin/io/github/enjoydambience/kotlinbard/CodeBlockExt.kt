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

/**
 * Short for [codeBlock]
 */
public inline fun cb(config: CodeBlockBuilder.() -> Unit): CodeBlock = codeBlock(config)


/**
 * Short for [CodeBlock.of]
 */
public fun cb(format: String, vararg args: Any?): CodeBlock = CodeBlock.of(format, *args)

/**
 * Returns a code block with this content as **code**.
 *
 * @see strLiteral
 * @see template
 */
public val String.code: CodeBlock get() = CodeBlock.of(this)

/**
 * Returns a code block with `this` as format, and given [args] as arguments.
 */
public fun String.code(vararg args: Any?): CodeBlock = CodeBlock.of(this, *args)

/**
 * Returns a code block that represents a _string literal_ with `this`'s contents
 *
 * @see template
 * @see code
 */
public val String.strLiteral: CodeBlock get() = CodeBlock.of("%S", this)

/**
 * Returns a code block that represents a _string template_ with `this`'s contents
 *
 * @see strLiteral
 * @see code
 */
public val String.template: CodeBlock get() = CodeBlock.of("%P", this)

/**
 * Returns a code block that represents this number literal.
 */
public val Number.literal: CodeBlock get() = CodeBlock.of("%L", this)

/**
 * An empty code block.
 */
public val emptyCodeBlock: CodeBlock = CodeBlock.builder().build()
