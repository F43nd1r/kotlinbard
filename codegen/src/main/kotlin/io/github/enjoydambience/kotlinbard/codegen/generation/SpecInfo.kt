package io.github.enjoydambience.kotlinbard.codegen.generation

import com.squareup.kotlinpoet.*
import kotlin.reflect.KClass
import kotlin.reflect.full.companionObject

/**
 * Reflection-based info about a `XXXSpec`, such as `FileSpec`, `TypeSpec`, etc.
 */
class SpecInfo private constructor(
    /** Class of the spec. */
    val specClass: KClass<*>
) {
    /** Name of spec, minus "Spec" suffix */
    val name = specClass.simpleName!!.removeSuffix("Spec")
    val companionClass = specClass.companionObject!!

    /** Class of the builder of spec (e.g. `FileSpec.Builder::class`) */
    val builderClass = specClass.nestedClasses.first { it.simpleName == "Builder" }

    override fun toString(): String = "SpecInfo(${specClass.simpleName})"

    companion object {
        fun of(klass: KClass<*>): SpecInfo? = allSpecsMap[klass]

        val allSpecsMap = listOf(
            FileSpec::class,
            TypeSpec::class,
            PropertySpec::class,
            FunSpec::class,
            ParameterSpec::class,
            TypeAliasSpec::class,
            AnnotationSpec::class
        ).associateWith { SpecInfo(it) }

        val allSpecs get() = allSpecsMap.values
    }
}
