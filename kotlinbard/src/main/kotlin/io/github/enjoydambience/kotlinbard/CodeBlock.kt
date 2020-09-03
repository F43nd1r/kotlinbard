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

public fun codeBlock(format: String, vararg args: Any?): CodeBlock = CodeBlock.of(format, *args)

/**
 * Creates a CodeBlock, with [this] as its format and the given [args].
 */
public fun String.codeFmt(vararg args: Any?): CodeBlock = CodeBlock.of(this, *args)
