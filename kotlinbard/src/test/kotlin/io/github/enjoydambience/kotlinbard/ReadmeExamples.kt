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

class ReadmeExamples : StringSpec({
    "control flow"{
        val function = function("analyzeTaco") {
            addCode { //functions are defined in the CodeBlockBuilder scope
                "taco.let" {
                    -"println(it)"
                }
                If("taco.isSpicy()") {
                    -"println(%S)".fmt("spicy!!")
                }.ElseIf("me.isHungry") {
                    -"eat(taco)"
                } Else {
                    -"saveForLater(taco)"
                }

                Do {
                    -"makeTaco()"
                }.While("tacos < 5")

                For("taco in tacos") {
                    -"println(%P)".fmt("taco information: \$taco")
                }

                When("taco") {
                    "is SpicyTaco" - {
                        -"println(%S)".fmt("Spicy!!")
                    }
                    "else" - "eat(%L)".fmt("taco")
                }
            }
        }
//        println(function)
    }
    "prop" {
        val prop = property("prop", String::class) {
            get {
                addStatement("return field")
            }
            set("value") {
                addStatement("field = value")
            }
            //or, for parameterless set
//            set {
//                addModifiers(KModifier.PRIVATE)
//            }
        }
//        println(prop)
    }
    "Code block shortcuts"{
        val myCode = "doStuff()".code
        val print = "println(%S)".code("Hello, World")
        val literal = "string literal".strLiteral
    }
    "function params" {
        val function = function("foo") {
            params {
                "string" of String::class
                "number" of Int::class init 5.literal
                ("args" of Any::class) { addModifiers(VARARG) }
            }
        }
//        println(function)
    }
})
