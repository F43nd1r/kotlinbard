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

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.KModifier.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

@OptIn(ExperimentalStdlibApi::class)
class ExamplesTest : StringSpec({
    "KotlinPoet's first example"{
        val withBard = buildFile("", "HelloWorld") {
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
    "kotlin.collections.Collection" {
        val file = buildFile("kotlin.collections", "Collections") {
            addInterface("Collection") {
                val E = TypeVariableName("E")
                val outE = TypeVariableName("E", variance = OUT)
                val unsafeE = E.plusAnnotations(buildAnnotation(UnsafeVariance::class))
                addKdoc(
                    """
                     A generic collection of elements. Methods in this interface support only read-only access to the collection;
                     read/write access is supported through the [MutableCollection] interface.
                     @param E the type of elements contained in the collection. The collection is covariant on its element type.
                """.trimIndent()
                )
                addTypeVariable(outE)
                addSuperinterface(ITERABLE.parameterizedBy(E))
                addProperty("size", Int::class, OVERRIDE) {
                    addKdoc("Returns the size of the collection.")
                }
                addFunction("isEmpty") {
                    addKdoc("Returns `true` if the collection is empty (contains no elements), `false` otherwise.")
                    addModifiers(OVERRIDE, ABSTRACT)
                    returns(Boolean::class)
                }
                addFunction("contains") {
                    addKdoc("Checks if the specified element is contained in this collection.")
                    addModifiers(OVERRIDE, ABSTRACT)
                    addParameter("element", unsafeE)
                    returns(Boolean::class)
                }
                addFunction("iterator") {
                    addModifiers(OVERRIDE, ABSTRACT)
                    returns(Iterator::class.asTypeName().parameterizedBy(E))
                }
                addFunction("containsAll") {
                    addModifiers(OVERRIDE, ABSTRACT)
                    addKdoc("Checks if all elements in the specified collection are contained in this collection.")
                    addParameter("elements", COLLECTION.parameterizedBy(unsafeE))
                    returns(Boolean::class)
                }
            }
        }
        file.toString() shouldBe """
package kotlin.collections

import kotlin.Boolean
import kotlin.Int
import kotlin.UnsafeVariance

/**
 * A generic collection of elements. Methods in this interface support only read-only access to the
 * collection;
 * read/write access is supported through the [MutableCollection] interface.
 * @param E the type of elements contained in the collection. The collection is covariant on its
 * element type.
 */
public interface Collection<out E> : Iterable<E> {
  /**
   * Returns the size of the collection.
   */
  public override val size: Int

  /**
   * Returns `true` if the collection is empty (contains no elements), `false` otherwise.
   */
  public override fun isEmpty(): Boolean

  /**
   * Checks if the specified element is contained in this collection.
   */
  public override fun contains(element: @UnsafeVariance E): Boolean

  public override fun iterator(): Iterator<E>

  /**
   * Checks if all elements in the specified collection are contained in this collection.
   */
  public override fun containsAll(elements: Collection<@UnsafeVariance E>): Boolean
}

        """.trimMargin()
    }
    "modify" {
        val func = buildFunction("foo") {
            addParameter("s", String::class)
        }
        val modified = func.modify(name = "bar") {
            addModifiers(PRIVATE)
            addParameter("i", Int::class)
        }
        modified.toString() shouldBe """
            private fun bar(s: kotlin.String, i: kotlin.Int): kotlin.Unit {
            }
            
        """.trimIndent()
    }
})
