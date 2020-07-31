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

import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.KModifier.VARARG
import io.kotest.core.spec.style.StringSpec

class ReadmeExamples : StringSpec({
    "control flow"{
        val function = buildFunction("analyzeTaco") {
            addCode { //functions are defined in the CodeBlockBuilder scope
                controlFlow("taco.let") {
                    addStatement("println(it)")
                }
                If("taco.isSpicy()") {
                    addStatement("println(%S)", "spicy!!")
                }.ElseIf("me.isHungry") {
                    addStatement("eat(taco)")
                } Else {
                    addStatement("saveForLater(taco)")
                }

                Do {
                    addStatement("makeTaco()")
                }.While("tacos < 5")

                For("taco in tacos") {
                    addStatement("println(%P)", "taco information: \$taco")
                }

                When("taco") {
                    e("is SpicyTaco") then {
                        addStatement("println(%S)", "Spicy!!")
                    }
                    Else("eat(%L)", "taco")
                }
            }
        }
        println(function)
    }
    "prop" {
        val prop = buildProperty("prop", String::class) {
            get {
                addStatement("return field")
            }
            set("value", String::class) {
                addStatement("field = value")
            }
            //or, for parameterless set
            set {
                addModifiers(KModifier.PRIVATE)
            }
        }
        println(prop)
    }
    "Code block shortcuts"{
        val myCode = "doStuff()".code
        val print = "println(%S)".code("Hello, World")
        val literal = "string literal".strLiteral
    }
    "function params" {
        val function = buildFunction("foo") {
            params {
                "string" of String::class
                "number" of Int::class init 5.literal
                ("args" of Any::class) { addModifiers(VARARG) }
            }
        }
        println(function)
    }
})
