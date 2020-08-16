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
import com.squareup.kotlinpoet.CodeBlock

@PublishedApi
internal fun FileSpec.Builder.wrapBuilder() = this

@PublishedApi
internal fun TypeSpec.Builder.wrapBuilder() = this

@PublishedApi
internal fun PropertySpec.Builder.wrapBuilder() = this

@PublishedApi
internal fun FunSpec.Builder.wrapBuilder() = this

@PublishedApi
internal fun ParameterSpec.Builder.wrapBuilder() = this

@PublishedApi
internal fun TypeAliasSpec.Builder.wrapBuilder() = this

@PublishedApi
internal fun AnnotationSpec.Builder.wrapBuilder() = this

@PublishedApi
internal fun CodeBlock.Builder.wrapBuilder() = this
