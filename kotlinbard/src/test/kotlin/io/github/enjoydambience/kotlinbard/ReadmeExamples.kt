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

import com.squareup.kotlinpoet.TypeVariableName
import com.squareup.kotlinpoet.asTypeName
import io.kotest.core.spec.style.StringSpec

class ReadmeExamples : StringSpec({
    "control flow"{
        val function = buildFunction("analyzeTaco") {
            addCode { //functions are defined in the CodeBlockBuilder scope
                controlFlow("taco.let") {
                    addStatement("println(it)")
                }
                `if`("taco.isSpicy()") {
                    addStatement("println(%S)", "spicy!!")
                }.`else if`("me.isHungry") {
                    addStatement("eat(taco)")
                } `else` {
                    addStatement("saveForLater(taco)")
                }

                `do` {
                    addStatement("makeTaco()")
                }.`while`("tacos < 5")

                `for`("taco in tacos") {
                    addStatement("println(%P)", "taco information: \$taco")
                }

                `when`("taco") {
                    "is SpicyTaco" - {
                        addStatement("println(%S)", "Spicy!!")
                    }
                    "else" - "eat(%L)".codeFmt("taco")
                }
            }
        }
//        println(function)
    }
    "setter" {
        val mySetter = buildSetter("value") {
            addStatement("field = value")
        }

        val prop = buildProperty("prop", String::class) {
            mutable()
            setter("value") {
                addStatement("println(%S)", "setting!")
                addStatement("field = value")
            }
        }
    }
    "typeName" {
        class ATag

        val myTag = ATag()
        val annotationSpec = buildAnnotation(Deprecated::class)

        val myTypeVarName = TypeVariableName("T")
            .reified
            .plusTag(ATag::class, myTag)
        val myTypeDecName = Int::class.asTypeName()
            .plusAnnotations(annotationSpec)
    }
})
