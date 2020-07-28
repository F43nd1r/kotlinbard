/*
 *    Copyright 2020 Benjamin Ye
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package io.github.enjoydambience.kotlinbard

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.buildCodeBlock

/**
 * Creates a new CodeBlock by applying a [config] to a newly created builder.
 */
public inline fun createCodeBlock(config: CodeBlock.Builder.() -> Unit): CodeBlock = buildCodeBlock(config)

/**
 * Adds code to a function, by building a CodeBlock.
 */
public inline fun FunSpec.Builder.addCode(body: CodeBlock.Builder.() -> Unit) {
    addCode(createCodeBlock(body))
}
