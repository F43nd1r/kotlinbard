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

import com.squareup.kotlinpoet.*

public fun FileSpec.Builder.asKotlinBardBuilder(): FileSpecBuilder = FileSpecBuilder(this)

public fun TypeSpec.Builder.asKotlinBardBuilder(): TypeSpecBuilder = TypeSpecBuilder(this)

public fun PropertySpec.Builder.asKotlinBardBuilder(): PropertySpecBuilder = PropertySpecBuilder(this)

public fun FunSpec.Builder.asKotlinBardBuilder(): FunSpecBuilder = FunSpecBuilder(this)

public fun ParameterSpec.Builder.asKotlinBardBuilder(): ParameterSpecBuilder = ParameterSpecBuilder(this)

public fun TypeAliasSpec.Builder.asKotlinBardBuilder(): TypeAliasSpecBuilder = TypeAliasSpecBuilder(this)

public fun AnnotationSpec.Builder.asKotlinBardBuilder(): AnnotationSpecBuilder = AnnotationSpecBuilder(this)

public fun CodeBlock.Builder.asKotlinBardBuilder(): CodeBlockBuilder = CodeBlockBuilder(this)
