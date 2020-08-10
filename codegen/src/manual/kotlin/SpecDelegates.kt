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

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.asTypeName
import com.squareup.kotlinpoet.asTypeVariableName
import io.github.enjoydambience.kotlinbard.*
import kotlin.reflect.*
import kotlin.reflect.full.declaredMembers

/**
 * Generates classes which delegate everything to a Spec.
 *
 * The output file currently is not entirely semantically correct, and needs manual editing.
 */
class SpecDelegates : ManualFileGenerator() {
    private val delegateName = "poetType"
    override fun FileSpec.Builder.generate() {
        SpecInfo.allSpecs.forEach {
            addDelegateClass(it.specClass, it.name + "Delegate")
            addDelegateClass(it.builderClass, it.name + "BuilderDelegate")
        }
    }

    private fun FileSpecBuilder.addDelegateClass(klass: KClass<*>, name: String) = addClass(name) {
        addModifiers(KModifier.ABSTRACT)
        primaryConstructor {
            addParameter(delegateName, klass, KModifier.PUBLIC)
            addModifiers(KModifier.INTERNAL)
        }
        addProperty(delegateName, klass) { init { add(delegateName) } }

        klass.supertypes.forEach { t ->
            if ((t.classifier as? KClass<*>)?.java?.isInterface == true) {
                addSuperinterface(t.asTypeName(), delegateName)
            }
        }

        val superMembers = klass.supertypes.flatMap {
            (it.classifier as KClass<*>).declaredMembers
        }

        klass.declaredMembers.forEach {
            if (it.visibility != KVisibility.PUBLIC) return@forEach
            when (it) {
                is KProperty<*> -> delProperty(it, superMembers)
                is KFunction<*> -> delFunction(it, superMembers, klass)
                else -> error("member not a function or property")
            }
        }
    }

    private fun TypeSpecBuilder.delProperty(prop: KProperty<*>, superMembers: List<KCallable<*>>) {
        if (superMembers.any { it is KProperty && it.signature == prop.signature }) return
        addProperty(prop.name, prop.returnType.asTypeName()) {
            get {
                addStatement("return $delegateName.%N", prop.name)
            }
            if (prop is KMutableProperty<*>) {
                mutable()
                set("value") {
                    addStatement("$delegateName.%N = value", prop.name)
                }
            }
        }
    }

    private fun TypeSpecBuilder.delFunction(func: KFunction<*>, superMembers: List<KCallable<*>>, klass: KClass<*>) {
        if (superMembers.any { it is KFunction && it.signature == func.signature }) return
        addFunction(func.name) {
            addModifiers(KModifier.PUBLIC)
            addTypeVariables(func.typeParameters.map { it.asTypeVariableName() })
            if (func.returnType.classifier != klass) {
                returns(func.returnType.asTypeName())
                addCode("return ")
            }

            val (call, params) = reflectCodeCall(func, delegateName)
            addParameters(params)
            addCode(call)
        }
    }
}

private val kFunctionSigField = Class.forName("kotlin.reflect.jvm.internal.KFunctionImpl")
    .getDeclaredField("signature").apply { isAccessible = true }
private val KFunction<*>.signature: String?
    get() = kFunctionSigField[this] as String?
private val kPropertySigField = Class.forName("kotlin.reflect.jvm.internal.KPropertyImpl")
    .getDeclaredField("signature").apply { isAccessible = true }
private val KProperty<*>.signature: String?
    get() = kPropertySigField[this] as String?
