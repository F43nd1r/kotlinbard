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

    "if/else" - {
        "simple if" {
            val block = codeBlock {
                `if`("%L.isEmpty()", "taco") {
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
                val block = codeBlock {
                    `if`("%L.isEmpty()", "taco") {
                        addStatement("addToppings()")
                    } `else` {
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
                val block = codeBlock {
                    `if`("%L.isEmpty()", "taco") {
                        addStatement("addToppings()")
                    } `else` "eat(%L)".codeFmt("taco")
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
                `if`("%L.isEmpty()", "taco") {
                    addStatement("addToppings()")
                }.`else if`("%L()", "isHungry") {
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
            val block = codeBlock {
                `if`("%L.isEmpty()", "taco") {
                    addStatement("addToppings()")
                }.`else if`("%L()", "isHungry") {
                    addStatement("eat(taco)")
                } `else` {
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
            val block = codeBlock {
                `while`("%L.isHungry()", "me") {
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
            val block = codeBlock {
                `do` {
                    addStatement("eatATaco()")
                } `while` "me.isHungry()"
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
                `for`("i in 0..2") {
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
            val block = codeBlock {
                `when` {
                    "isOk()" - "println(%S)".codeFmt("OK")
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
                `when`("%L", "a") {
                    "3" - "println(%S)".codeFmt("is 3")
                }
            }
            block.toString() shouldBe """
                |when (a) {
                |  3 -> println("is 3")
                |}
                |""".trimMargin()
        }
        "branch {}" {
            val block = codeBlock {
                `when`("a") {
                    "3" - {
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
        "else" {
            val block = codeBlock {
                `when` {
                    "else" - "celebrate()"
                }

            }
            block.toString() shouldBe """
                |when {
                |  else -> celebrate()
                |}
                |""".trimMargin()
        }
        "case" {
            val block = codeBlock {
                `when` {
                    case("foo") {
                        addStatement("celebrate()")
                    }
                    case(codeBlock("bar")) {
                        addStatement("celebrate()")
                    }
                }
            }
            block.toString() shouldBe """
                |when {
                |  foo -> {
                |    celebrate()
                |  }
                |  bar -> {
                |    celebrate()
                |  }
                |}
                |""".trimMargin()
        }
    }
})
