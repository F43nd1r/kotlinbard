package io.github.enjoydambience.kotlinbard

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeName
import java.lang.reflect.Type
import kotlin.reflect.KClass

/**
 * Creates a setter for this property.
 *
 * This will also automatically add the setter parameter
 * with the given [paramName] and [type].
 */
public inline fun PropertySpec.Builder.set(
    paramName: String = "value",
    type: TypeName,
    config: FunSpec.Builder.() -> Unit
): PropertySpec.Builder = setter(createSetter {
    addParameter(paramName, type)
    config()
})

/**
 * Creates a setter for this property.
 *
 * This will also automatically add the setter parameter
 * with the given [paramName] and [type].
 */
public inline fun PropertySpec.Builder.set(
    paramName: String = "value",
    type: Type,
    config: FunSpec.Builder.() -> Unit
): PropertySpec.Builder = setter(createSetter {
    addParameter(paramName, type)
    config()
})

/**
 * Creates a setter for this property.
 *
 * This will also automatically add the setter parameter
 * with the given [paramName] and [type].
 */
public inline fun PropertySpec.Builder.set(
    paramName: String = "value",
    type: KClass<*>,
    config: FunSpec.Builder.() -> Unit
): PropertySpec.Builder = setter(createSetter {
    addParameter(paramName, type)
    config()
})
