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

package io.github.enjoydambience.kotlinbard

import com.squareup.kotlinpoet.KModifier.VARARG
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ParameterDslTest : StringSpec({
    "fun parameters"{
        val function = buildFunction("foo") {
            params {
                "simple" of String::class
                "withInit" of Int::class init 5.literal
                "reified".of<Int>()
                "withConfig".of<Any>()() {
                    addModifiers(VARARG)
                }
            }
        }
        function.toString() shouldBe """
            fun foo(
              simple: kotlin.String,
              withInit: kotlin.Int = 5,
              reified: kotlin.Int,
              vararg withConfig: kotlin.Any
            ) {
            }

        """.trimIndent()
    }
})
