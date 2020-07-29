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

package io.github.enjoydambience.kotlinbard

import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe

class ControlFlowTest : FreeSpec({
    "control flow" {
        val block = buildCodeBlock {
            controlFlow("taco.let") {
                addStatement("eat(it)")
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
            val block = buildCodeBlock {
                If("%L.isEmpty()", "taco") {
                    addStatement("addToppings()")
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
                val block = buildCodeBlock {
                    If("%L.isEmpty()", "taco") {
                        addStatement("addToppings()")
                    } Else {
                        addStatement("eat(taco)")
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
                val block = buildCodeBlock {
                    If("%L.isEmpty()", "taco") {
                        addStatement("addToppings()")
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
            val block = buildCodeBlock {
                If("%L.isEmpty()", "taco") {
                    addStatement("addToppings()")
                }.ElseIf("%L()", "isHungry") {
                    addStatement("eat(taco)")
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
            val block = buildCodeBlock {
                If("%L.isEmpty()", "taco") {
                    addStatement("addToppings()")
                }.ElseIf("%L()", "isHungry") {
                    addStatement("eat(taco)")
                } Else {
                    addStatement("saveForLater(taco)")
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
            val block = buildCodeBlock {
                While("%L.isHungry()", "me") {
                    addStatement("eatATaco()")
                }
            }
            block.toString() shouldBe """
                |while (me.isHungry()) {
                |  eatATaco()
                |}
                |""".trimMargin()
        }
        "do while" {
            val block = buildCodeBlock {
                Do {
                    addStatement("eatATaco()")
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
            val block = buildCodeBlock {
                For("i in 0..2") {
                    addStatement("println(i)")
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
            val block = buildCodeBlock {
                When {
                    e("isOk()").then("println(%S)", "OK")
                }
            }
            block.toString() shouldBe """
                |when {
                |  isOk() -> println("OK")
                |}
                |""".trimMargin()
        }
        "with arg" {
            val block = buildCodeBlock {
                When("%L", "a") {
                    e("3").then("println(%S)", "is 3")
                }
            }
            block.toString() shouldBe """
                |when (a) {
                |  3 -> println("is 3")
                |}
                |""".trimMargin()
        }
        "then {}" {
            val block = buildCodeBlock {
                When("a") {
                    e("3") then {
                        addStatement("println(%S)", "is 3")
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
            val block = buildCodeBlock {
                When {
                    Else("celebrate()")
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
        val func = buildFunction("foo") {
            addCode {
                If("a") {
                    addStatement("b")
                }.ElseIf("c") {
                    addStatement("d")
                }.Else("e")
                While("f") {
                    Do {
                        addStatement("g")
                    }.While("h")
                }
                For("i in j") {
                    addStatement("k")
                }
                When {
                    e("l") then {
                        addStatement("s")
                    }
                    Else {
                        addStatement("n")
                    }
                }
                When("o") {
                    e("in p").then("q")
                    e("!is %T", Int::class).then("r")
                    Else("s")
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
                l -> {
                  s
                }
                else -> {
                  n
                }
              }
              when (o) {
                in p -> q
                !is kotlin.String -> r
                else -> s
              }
            }
            
            """.trimIndent()
    }
})
