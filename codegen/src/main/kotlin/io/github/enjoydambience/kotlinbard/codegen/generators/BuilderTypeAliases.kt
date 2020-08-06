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

import com.squareup.kotlinpoet.FileSpec
import io.github.enjoydambience.kotlinbard.CodegenDsl
import io.github.enjoydambience.kotlinbard.addTypeAlias
import io.github.enjoydambience.kotlinbard.buildAnnotation
import io.github.enjoydambience.kotlinbard.codegen.SpecInfo

/**
 * Creates typeAliases for all spec builders.
 *
 * These aliases are used so builders can have a DSL annotation.
 */
object BuilderTypeAliases : FileGenerator {
    private val dslAnnotation = buildAnnotation(CodegenDsl::class)

    override fun FileSpec.Builder.generate() {
        SpecInfo.allSpecs.forEach {
            addTypeAlias(it.builderName.simpleName, it.builderClass) {
                addAnnotation(dslAnnotation)
            }
        }
    }

}
