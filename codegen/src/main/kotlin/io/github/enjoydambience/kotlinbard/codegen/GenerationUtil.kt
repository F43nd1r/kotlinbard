package io.github.enjoydambience.kotlinbard.codegen


import com.squareup.kotlinpoet.*
import io.github.enjoydambience.kotlinbard.createParameter
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
 * Right now, only supports non-suspending kotlin functions.
 */
//todo: add tests
fun reflectCodeCall(function: KFunction<*>, receiverOrInstance: String? = null): Pair<CodeBlock, List<ParameterSpec>> {
    val receiverParam = function.instanceParameter ?: function.extensionReceiverParameter
    require(!(receiverParam == null && receiverOrInstance != null)) {
        "Cannot use receiver on function with no receiver/instance:$function"
    }
    val receiverClass = receiverParam?.type?.classifier as? KClass<*>
    return when {
        receiverClass?.isCompanion == true -> {
            require(receiverOrInstance == null) { "Cannot use receiver on companion object call" }
            callCompanion(function, receiverClass)
        }
        receiverParam != null && receiverOrInstance != null -> callWithReceiver(function, receiverOrInstance)
        else -> callWithoutReceiver(function)
    }
}

/**
 * Given a FunSpec, returns a CodeBlock that represents calling that function.
 *
 * Right now only works for top-level functions (or functions with implicit recievers).
 */
fun codeCallNoReceiver(function: FunSpec): CodeBlock {
    val paramsCall = getParamsCall(function.parameters)
    return CodeBlock.of("%N($paramsCall)", function)
}

private fun callCompanion(function: KFunction<*>, companionClass: KClass<*>): Pair<CodeBlock, List<ParameterSpec>> {
    val (params, paramsCall) = getParameters(function)
    return CodeBlock.of("%T.%N($paramsCall)", companionClass.declaringClass!!.asClassName(), function.name) to params
}

private fun callWithReceiver(function: KFunction<*>, receiver: String): Pair<CodeBlock, List<ParameterSpec>> {
    val (params, paramsCall) = getParameters(function)
    return CodeBlock.of("%N.%N($paramsCall)", receiver, function.name) to params
}

private fun callWithoutReceiver(function: KFunction<*>): Pair<CodeBlock, List<ParameterSpec>> {
    val (params, paramsCall) = getParameters(function)
    return CodeBlock.of("%N($paramsCall)") to params
}

private fun getParameters(function: KFunction<*>): Pair<List<ParameterSpec>, String> {
    val params = function.parameters
        .filter { it.kind == KParameter.Kind.VALUE }
        .map {
            if (it.isVararg) {
                adaptVarargParameter(it)
            } else {
                createParameter(it.name!!, it.type.asTypeName())
            }
        }
    val paramsCall = getParamsCall(params)
    return Pair(params, paramsCall)
}

private fun getParamsCall(params: List<ParameterSpec>): String = params.joinToString {
    it.name + "=" + (if (KModifier.VARARG in it.modifiers) "*" else "") + it.name
}

private fun adaptVarargParameter(it: KParameter): ParameterSpec =
    createParameter(
        it.name!!,
        it.type.arguments.first().type!!.asTypeName(),
        KModifier.VARARG
    )

/**
 * Configure a function to represents calling another [function].
 *
 * This sets return type, parameters, and code.
 */
fun FunSpec.Builder.delegatesTo(function: KFunction<*>) {
    returns(function.returnType.asTypeName())
    val (call, params) = reflectCodeCall(function)
    addParameters(params)
    addCode("return ")
    addCode(call)
}

/**
 * Copies any deprecation to this function if present on the given [element].
 */
fun FunSpec.Builder.copyDeprecationOf(element: KAnnotatedElement) {
    val deprecation = element.annotations.filterIsInstance<Deprecated>().singleOrNull()
    if (deprecation != null) {
        addAnnotation(AnnotationSpec.get(deprecation))
    }
}
