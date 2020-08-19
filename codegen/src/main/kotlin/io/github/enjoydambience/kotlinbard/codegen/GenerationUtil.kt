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

package io.github.enjoydambience.kotlinbard.codegen


import com.squareup.kotlinpoet.*
import io.github.enjoydambience.kotlinbard.addAnnotation
import io.github.enjoydambience.kotlinbard.buildParameter
import io.github.enjoydambience.kotlinbard.modify
import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.full.extensionReceiverParameter
import kotlin.reflect.full.instanceParameter

/**
 * Returns a CodeBlock that represents calling a given [function].
 *
 * A [receiverOrInstance] can be given and will be inserted in front of the call.
 *
 * This works with vararg and default values.
 *
 * Right now, only supports simple-ish kotlin functions.
 */
//todo: add tests
fun codeCallReflected(
    function: KFunction<*>,
    receiverOrInstance: String? = null
): Pair<CodeBlock, List<ParameterSpec>> {
    val receiverParam = function.instanceParameter ?: function.extensionReceiverParameter
    require(!(receiverParam == null && receiverOrInstance != null)) {
        "Cannot use receiver on function with no receiver/instance:$function"
    }
    val receiverClass = receiverParam?.type?.classifier as? KClass<*>
    val (params, paramsCall) = getParamsAndCall(function)
    return when {
        receiverClass?.isCompanion == true -> {
            require(receiverOrInstance == null) { "Cannot use receiver on companion object call" }
            CodeBlock.of("%T.%N(%L)", receiverClass.declaringClass!!.asClassName(), function.name, paramsCall) to params
        }
        receiverParam != null && receiverOrInstance != null -> {
            CodeBlock.of("%N.%N(%L)", receiverOrInstance, function.name, paramsCall) to params
        }
        else -> {
            CodeBlock.of("%N(%L)", function.name, paramsCall) to params
        }
    }
}

/**
 * Given a FunSpec, returns a CodeBlock that represents calling that function.
 */
fun codeCall(function: FunSpec, receiverOrInstance: String? = null, named: Boolean = true): CodeBlock {
    //no receiver check
    val callParams = getCallParams(function.parameters, named)
    return if (receiverOrInstance != null) {
        CodeBlock.of("%L.%N(%L)", receiverOrInstance, function, callParams)
    } else {
        CodeBlock.of("%N(%L)", function, callParams)
    }
}

private fun getParamsAndCall(function: KFunction<*>): Pair<List<ParameterSpec>, CodeBlock> {
    val params = function.parameters
        .filter { it.kind == KParameter.Kind.VALUE }
        .map {
            if (it.isVararg) {
                buildParameter(
                    it.name!!,
                    it.type.arguments.first().type!!.asTypeName(),
                    KModifier.VARARG
                )
            } else {
                buildParameter(it.name!!, it.type.asTypeName())
            }
        }
    val paramsCall = getCallParams(params)
    return Pair(params, paramsCall)
}

private fun getCallParams(params: List<ParameterSpec>, named: Boolean = true): CodeBlock = params.map {
    val name = it.name
    if (named) {
        CodeBlock.of("%N=%N", name, name)
    } else {
        CodeBlock.of("%N", name)
    }
}.joinToCode()

/**
 * Copies any deprecation to this function if present on the given [element].
 */
fun FunSpec.Builder.copyDeprecationOf(element: KAnnotatedElement) {
    val deprecation = element.annotations.singleOrNull { it is Deprecated } as Deprecated?
    if (deprecation != null) {
        addAnnotation(AnnotationSpec.get(deprecation))
    }
}

fun deprecatedDelegate(function: FunSpec, newName: String): FunSpec {
    return function.modify(newName) {
        clearBody()
        addCode("return %L", codeCall(function))
        val deprecated = Deprecated::class.asTypeName()
        annotations.removeAll {
            it.typeName == deprecated
        }
        addAnnotation(Deprecated::class) {
            addMember("%S", "use ${function.name} instead")
            val receiver = if (function.receiverType != null) "this" else null
            addMember("ReplaceWith(%S)", codeCall(function, receiver, false))
        }
    }
}
