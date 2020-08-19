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

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class ControlFlowTest : FreeSpec({
    "control flow" {
        val block = codeBlock {
            "taco.let" {
                -"eat(it)"
            }
        }
        block.toString() shouldBe """
            |taco.let {
            |  eat(it)
            |}
            |""".trimMargin()
    }

    "if" - {
        "simple if" {
            val block = codeBlock {
                If("%L.isEmpty()", "taco") {
                    -"addToppings()"
                }
            }
            block.toString() shouldBe """
                |if (taco.isEmpty()) {
                |  addToppings()
                |}
                |""".trimMargin()
        }
        "else" - {
            "else {}" {
                val block = codeBlock {
                    If("%L.isEmpty()", "taco") {
                        -"addToppings()"
                    } Else {
                        -"eat(taco)"
                    }
                }
                block.toString() shouldBe """
                    |if (taco.isEmpty()) {
                    |  addToppings()
                    |}
                    |else {
                    |  eat(taco)
                    |}
                    |""".trimMargin()
            }
            "else (format)" {
                val block = codeBlock {
                    If("%L.isEmpty()", "taco") {
                        -"addToppings()"
                    }.Else("eat(%L)", "taco")
                }
                block.toString() shouldBe """
                    |if (taco.isEmpty()) {
                    |  addToppings()
                    |}
                    |else eat(taco)
                    |""".trimMargin()
            }
        }
        "else if" {
            val block = codeBlock {
                If("%L.isEmpty()", "taco") {
                    -"addToppings()"
                }.ElseIf("%L()", "isHungry") {
                    -"eat(taco)"
                }
            }
            block.toString() shouldBe """
                |if (taco.isEmpty()) {
                |  addToppings()
                |}
                |else if (isHungry()) {
                |  eat(taco)
                |}
                |""".trimMargin()
        }
        "else if else" {
            val block = codeBlock {
                If("%L.isEmpty()", "taco") {
                    -"addToppings()"
                }.ElseIf("%L()", "isHungry") {
                    -"eat(taco)"
                } Else {
                    -"saveForLater(taco)"
                }
            }
            block.toString() shouldBe """
                |if (taco.isEmpty()) {
                |  addToppings()
                |}
                |else if (isHungry()) {
                |  eat(taco)
                |}
                |else {
                |  saveForLater(taco)
                |}
                |""".trimMargin()
        }
    }
    "while" - {
        "simple while" {
            val block = codeBlock {
                While("%L.isHungry()", "me") {
                    -"eatATaco()"
                }
            }
            block.toString() shouldBe """
                |while (me.isHungry()) {
                |  eatATaco()
                |}
                |""".trimMargin()
        }
        "do while" {
            val block = codeBlock {
                Do {
                    -"eatATaco()"
                }.While("%L.isHungry()", "me")
            }
            block.toString() shouldBe """
                |do {
                |  eatATaco()
                |} while (me.isHungry())
                |""".trimMargin()
        }
    }
    "for" - {
        "simple for" {
            val block = codeBlock {
                For("i in 0..2") {
                    -"println(i)"
                }
            }
            block.toString() shouldBe """
                |for (i in 0..2) {
                |  println(i)
                |}
                |""".trimMargin()
        }
    }
    "when" - {
        "without arg" {
            val block = codeBlock {
                When {
                    "isOk()" - "println(%S)".fmt("OK")
                }
            }
            block.toString() shouldBe """
                |when {
                |  isOk() -> println("OK")
                |}
                |""".trimMargin()
        }
        "with arg" {
            val block = codeBlock {
                When("%L", "a") {
                    3.literal - "println(%S)".fmt("is 3")
                }
            }
            block.toString() shouldBe """
                |when (a) {
                |  3 -> println("is 3")
                |}
                |""".trimMargin()
        }
        "then {}" {
            val block = codeBlock {
                When("a") {
                    3.literal - {
                        -"println(%S)".fmt("is 3")
                    }
                }
            }
            block.toString() shouldBe """
                |when (a) {
                |  3 -> {
                |    println("is 3")
                |  }
                |}
                |""".trimMargin()
        }
        "else"{
            val block = codeBlock {
                When {
                    "else" - "celebrate()"
                }

            }
            block.toString() shouldBe """
                |when {
                |  else -> celebrate()
                |}
                |""".trimMargin()
        }
    }
    "full house" {
        val func = function("foo") {
            addCode {
                If("a") {
                    -"b"
                }.ElseIf("c") {
                    -"d"
                }.Else("e")
                While("f") {
                    Do {
                        -"g"
                    }.While("h")
                }
                For("i in j") {
                    -"k"
                }
                When {
                    "l" - "s"
                    "else" - { -"n" }
                }
                When("o") {
                    "in p" - "q".code
                    "!is %T".fmt(Int::class) - "r"
                    "else" - "s".code
                }
            }
        }
        func.toString() shouldBe """
            fun foo() {
              if (a) {
                b
              }
              else if (c) {
                d
              }
              else e
              while (f) {
                do {
                  g
                } while (h)
              }
              for (i in j) {
                k
              }
              when {
                l -> s
                else -> {
                  n
                }
              }
              when (o) {
                in p -> q
                !is kotlin.Int -> r
                else -> s
              }
            }
            
            """.trimIndent()
    }
})
