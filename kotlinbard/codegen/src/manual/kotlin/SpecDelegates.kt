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
import com.squareup.kotlinpoet.metadata.KotlinPoetMetadataPreview
import com.squareup.kotlinpoet.metadata.specs.toTypeSpec
import io.github.enjoydambience.kotlinbard.*
import io.github.enjoydambience.kotlinbard.codegen.generators.FileGenerator
import io.kotest.core.spec.style.StringSpec
import kotlinx.coroutines.launch

/**
 * Generates classes which delegate everything to a Spec.
 *
 * The output file currently is not entirely semantically correct, and needs manual editing.
 */
class SpecDelegates : StringSpec({
    "allSpecs"{
        SpecInfo.allSpecs.forEach {
            launch {
                genForSpec(it).manualGenerate()
            }
        }
    }
})

private const val delegateName = "poetBuilder"

private fun genForSpec(spec: SpecInfo): FileGenerator {
    return object : FileGenerator {
        private val name = spec.specClass.simpleName + "Builder"
        override val fileName: String get() = name

        override fun FileSpec.Builder.generate() {
            generateBuilderDelegate(spec, name)
        }
    }
}

@OptIn(KotlinPoetMetadataPreview::class)
private fun FileSpecBuilder.generateBuilderDelegate(spec: SpecInfo, name: String) {
    val klass = spec.poetBuilderClass
    val typeName: TypeName = ClassName(destinationPackage, name)

    addFunction(name) {
        addParameter(delegateName, spec.poetBuilderClass)
        returns(typeName)
        addStatement("return %T(%N, false)", typeName, delegateName)
    }

    val origSpec = klass.toTypeSpec()
    addClass(name) {
        addAnnotation(CodegenDsl::class)
        primaryConstructor {
            addParameter(delegateName, klass, KModifier.PUBLIC)
            addParameter("dummy", Boolean::class)
            addModifiers(KModifier.INTERNAL)
        }
        addProperty(delegateName, klass) { init { add(delegateName) } }

        origSpec.superinterfaces.forEach { (k, v) ->
            addSuperinterface(k, delegateName)
        }
        origSpec.propertySpecs.forEach { prop ->
            delProperty(prop)
        }
        origSpec.funSpecs.forEach { func ->
            delFunction(func, klass.asTypeName())
        }
    }
}

private fun TypeSpecBuilder.delProperty(prop: PropertySpec) {
    if (KModifier.INTERNAL in prop.modifiers
        || KModifier.PRIVATE in prop.modifiers
        || KModifier.PROTECTED in prop.modifiers
    ) return
    addProperty(prop.name, prop.type) {
        get {
            addStatement("return $delegateName.%N", prop.name)
        }
        if (prop.mutable) {
            mutable()
            set("value") {
                addStatement("$delegateName.%N = value", prop.name)
            }
        }
    }
}

@OptIn(KotlinPoetMetadataPreview::class)
private fun TypeSpecBuilder.delFunction(func: FunSpec, klass: TypeName) {
    if (KModifier.INTERNAL in func.modifiers
        || KModifier.PRIVATE in func.modifiers
        || KModifier.PROTECTED in func.modifiers
    ) return
    addFunction(func.name) {
        addModifiers(KModifier.PUBLIC)
        addTypeVariables(func.typeVariables)
        val returnType = func.returnType
        if (returnType != klass) {
            returnType?.let { returns(it) }
            addCode("return ")
        }
        addParameters(func.parameters)
        addCode(codeCall(func, delegateName))
    }
}
