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
        val block = createCodeBlock {
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
            val block = createCodeBlock {
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
                val block = createCodeBlock {
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
                val block = createCodeBlock {
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
            val block = createCodeBlock {
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
            val block = createCodeBlock {
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
            val block = createCodeBlock {
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
            val block = createCodeBlock {
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
            val block = createCodeBlock {
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
            val block = createCodeBlock {
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
            val block = createCodeBlock {
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
            val block = createCodeBlock {
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
            val block = createCodeBlock {
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
        "in"{
            val block = createCodeBlock {
                When("a") {
                    In("b").then("0")
                }
            }
            block.toString() shouldBe """
                |when (a) {
                |  in b -> 0
                |}
                |""".trimMargin()
        }
        "!in"{
            val block = createCodeBlock {
                When("a") {
                    nIn("b").then("0")
                }
            }
            block.toString() shouldBe """
                |when (a) {
                |  !in b -> 0
                |}
                |""".trimMargin()
        }
        "is"{
            val block = createCodeBlock {
                When("a") {
                    Is(String::class).then("0")
                }
            }
            block.toString() shouldBe """
                |when (a) {
                |  is kotlin.String -> 0
                |}
                |""".trimMargin()
        }
        "!is"{
            val block = createCodeBlock {
                When("a") {
                    nIs(String::class).then("0")
                }
            }
            block.toString() shouldBe """
                |when (a) {
                |  !is kotlin.String -> 0
                |}
                |""".trimMargin()
        }
    }
    "full house" {
        val func = createFunction("foo") {
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
                    In("p").then("q")
                    nIn("r").then("s")
                    Is(Int::class).then("t")
                    Is(String::class).then("u")
                    Else("v")
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
                !in r -> s
                is kotlin.Int -> t
                is kotlin.String -> u
                else -> v
              }
            }
            
            """.trimIndent()
    }
})
