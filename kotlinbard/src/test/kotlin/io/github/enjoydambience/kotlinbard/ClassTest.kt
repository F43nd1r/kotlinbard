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

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ClassTest : StringSpec({
    "init block"{
        val type = buildClass("Foo") {
            init {
                -"println(%S)".fmt("init")
            }
        }
        type.toString() shouldBe """
            class Foo {
              init {
                println("init")
              }
            }
            
        """.trimIndent()
    }
    "multiple init block" {
        val type = buildClass("Foo") {
            init {
                -"println(%S)".fmt("init")
            }
            addProperty("foo", String::class) {
                initializer("%S", "foo")
            }
            init {
                -"println(%S)".fmt("init2")
            }
        }
        type.toString() shouldBe """
            class Foo {
              val foo: kotlin.String = "foo"
            
              init {
                println("init")
              }
              init {
                println("init2")
              }
            }

        """.trimIndent()
    }
    "secondary constructor" {
        val type = buildClass("Foo") {
            addConstructor {
                addParameter("foo", String::class)
                addStatement("println(%S)", "foo")
            }
        }
        type.toString() shouldBe """
            class Foo {
              constructor(foo: kotlin.String) {
                println("foo")
              }
            }

        """.trimIndent()
    }
})
