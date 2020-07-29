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

package io.github.enjoydambience.kotlinbard.codegen.generation

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec

/**
 * File generators that are based on reflecting methods on Spec classes.
 *
 * The generated FunSpecs can then be referenced from other generators.
 *
 * @see SpecInfo
 */
abstract class SpecFunctionFileGenerator(
    override val fileName: String
) : FileGenerator {

    protected abstract fun generateFunctionsForSpec(spec: SpecInfo): List<FunSpec>

    val functionsBySpec by lazy {
        SpecInfo.allSpecs.associateWith(this::generateFunctionsForSpec)
    }

    override fun FileSpec.Builder.generate() {
        functionsBySpec.values.asSequence().flatten().forEach {
            addFunction(it)
        }
    }
}
