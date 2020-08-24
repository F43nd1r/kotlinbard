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

package io.github.enjoydambience.kotlinbard.codegen.generators

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.UNIT
import io.github.enjoydambience.kotlinbard.buildFunction
import io.github.enjoydambience.kotlinbard.codegen.SpecInfo
import io.github.enjoydambience.kotlinbard.codegen.codeCallReflected
import kotlin.reflect.KFunction
import kotlin.reflect.full.declaredMemberFunctions

object SpecModifiers : SpecFunctionFileGenerator() {
    override fun generateFunctionsForSpec(spec: SpecInfo): List<FunSpec> {
        return spec.specClass.declaredMemberFunctions.filter {
            it.returnType.classifier == spec.builderClass
        }.map {
            generateFunction(spec, it)
        }
    }

    private fun generateFunction(spec: SpecInfo, function: KFunction<*>): FunSpec = buildFunction("modify") {
        addModifiers(KModifier.INLINE)
        receiver(spec.specClass)
        returns(spec.specClass)

        val (call, params) = codeCallReflected(function)
        addParameters(params)

        val configParam = LambdaTypeName.get(
            receiver = spec.builderName,
            returnType = UNIT
        )
        addParameter("config", configParam)

        addStatement("return %L.apply(config).build()", call)
    }
}
