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
import io.github.enjoydambience.kotlinbard.codegen.SpecInfo
import io.github.enjoydambience.kotlinbard.codegen.deprecatedDelegate
import net.pearx.kasechange.toPascalCase

object DeprecatedSpecBuilders : SpecFunctionFileGenerator() {
    override fun generateFunctionsForSpec(spec: SpecInfo): List<FunSpec> =
        SpecBuilders.functionsBySpec.getValue(spec).map {
            deprecatedDelegate(it, getOldName(it.name))
        }

    private fun getOldName(newName: String): String {
        if (newName == "enumClass") return "buildEnum"
        return "build" + newName.toPascalCase()
    }
}
