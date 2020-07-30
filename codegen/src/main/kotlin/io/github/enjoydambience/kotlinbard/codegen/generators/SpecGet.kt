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

package io.github.enjoydambience.kotlinbard.codegen.generators

import com.squareup.kotlinpoet.FunSpec
import io.github.enjoydambience.kotlinbard.buildFunction
import io.github.enjoydambience.kotlinbard.codegen.SpecInfo
import io.github.enjoydambience.kotlinbard.codegen.copyDeprecationOf
import io.github.enjoydambience.kotlinbard.codegen.delegatesTo
import kotlin.reflect.full.declaredMemberFunctions

/**
 * Generates functions that simply delegate to Spec companion functions.
 *
 * Derived from spec companion functions that return (their own) spec type.
 */
object SpecGet : SpecFunctionFileGenerator("_SpecGetters") {
    override fun generateFunctionsForSpec(spec: SpecInfo): List<FunSpec> =
        spec.companionClass.declaredMemberFunctions
            .filter {
                it.returnType.classifier == spec.specClass
            }
            .map { function ->
                buildFunction(function.name + spec.name) {
                    copyDeprecationOf(function)
                    delegatesTo(function)
                }
            }
}
