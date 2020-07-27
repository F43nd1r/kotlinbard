package io.github.enjoydambience.kotlinbard.codegen

import kotlin.reflect.KClass

val KClass<*>.declaringClass: KClass<*>?
    get() = java.declaringClass?.kotlin
