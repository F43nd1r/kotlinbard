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

import com.squareup.kotlinpoet.KModifier
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class PropertyTest : StringSpec({
    "getter" {
        val prop = buildProperty("prop", Int::class) {
            getter {
                addStatement("return 0")
            }
        }
        prop.toString() shouldBe """
            |val prop: kotlin.Int
            |  get() = 0
            |""".trimMargin()
    }
    "setter" {
        val prop = buildProperty("prop", Int::class) {
            mutable()
            setter("myValue") {
                addStatement("println(myValue)")
            }
        }
        prop.toString() shouldBe """
            |var prop: kotlin.Int
            |  set(myValue) {
            |    println(myValue)
            |  }
            |""".trimMargin()
    }
    "getter and setter" {
        val prop = buildProperty("prop", String::class) {
            mutable()
            getter {
                addStatement("return %S", "foo")
            }
            setter("value") {
                addStatement("println(value)")
            }
        }
        prop.toString() shouldBe """
            |var prop: kotlin.String
            |  get() = "foo"
            |  set(value) {
            |    println(value)
            |  }
            |""".trimMargin()
    }

    "empty setter" {
        val prop = buildProperty("prop", String::class) {
            mutable()
            setter {
                addModifiers(KModifier.PRIVATE)
            }
        }
        prop.toString() shouldBe """
            |var prop: kotlin.String
            |  private set
            |""".trimMargin()
    }
})
