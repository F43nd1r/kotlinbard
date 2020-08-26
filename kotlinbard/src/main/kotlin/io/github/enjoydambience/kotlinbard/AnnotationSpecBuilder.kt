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

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.CodeBlock
import kotlin.reflect.KClass

@Suppress("FunctionName")
public fun AnnotationSpecBuilder(poetBuilder: AnnotationSpec.Builder): AnnotationSpecBuilder =
    AnnotationSpecBuilder(poetBuilder, false)

@CodegenDsl
public class AnnotationSpecBuilder internal constructor(
    public val poetBuilder: AnnotationSpec.Builder,
    @Suppress("UNUSED_PARAMETER") dummy: Boolean,
) {
    public val members: MutableList<CodeBlock> get() = poetBuilder.members
    public val tags: MutableMap<KClass<*>, Any> get() = poetBuilder.tags
    public fun addMember(codeBlock: CodeBlock) {
        poetBuilder.addMember(codeBlock = codeBlock)
    }

    public fun addMember(format: String, vararg args: Any) {
        poetBuilder.addMember(format = format, args = args)
    }

    public fun build(): AnnotationSpec = poetBuilder.build()
    public fun useSiteTarget(useSiteTarget: AnnotationSpec.UseSiteTarget?) {
        poetBuilder.useSiteTarget(useSiteTarget = useSiteTarget)
    }
}
