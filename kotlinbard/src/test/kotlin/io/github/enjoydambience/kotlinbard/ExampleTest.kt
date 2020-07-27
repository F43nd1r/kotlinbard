package io.github.enjoydambience.kotlinbard

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.KModifier.VARARG
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ExampleTest : StringSpec({
    "KotlinPoet's first example"{
        val withDsl = createFile("", "HelloWorld") {
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
        val example = run {
            val greeterClass = ClassName("", "Greeter")
            val file = FileSpec.builder("", "HelloWorld")
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
            file
        }
        withDsl shouldBe example
    }

})
