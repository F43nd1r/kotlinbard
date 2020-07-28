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

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.KModifier.VARARG
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ExampleTest : StringSpec({
    "KotlinPoet's first example"{
        val withBard = createFile("", "HelloWorld") {
            val greeterClass = ClassName("", "Greeter")
            addClass(greeterClass) {
                primaryConstructor {
                    addParameter("name", String::class)
                }
                addProperty("name", String::class) {
                    initializer("name")
                }
                addFunction("greet") {
                    addStatement("println(%P)", "Hello, \$name")
                }
            }
            addFunction("main") {
                addParameter("args", String::class, VARARG)
                addStatement("%T(args[0]).greet()", greeterClass)
            }
        }
        val greeterClass = ClassName("", "Greeter")
        val withPoet = FileSpec.builder("", "HelloWorld")
            .addType(
                TypeSpec.classBuilder("Greeter")
                    .primaryConstructor(
                        FunSpec.constructorBuilder()
                            .addParameter("name", String::class)
                            .build()
                    )
                    .addProperty(
                        PropertySpec.builder("name", String::class)
                            .initializer("name")
                            .build()
                    )
                    .addFunction(
                        FunSpec.builder("greet")
                            .addStatement("println(%P)", "Hello, \$name")
                            .build()
                    )
                    .build()
            )
            .addFunction(
                FunSpec.builder("main")
                    .addParameter("args", String::class, VARARG)
                    .addStatement("%T(args[0]).greet()", greeterClass)
                    .build()
            )
            .build()
        withBard shouldBe withPoet
    }

})
