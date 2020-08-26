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
import java.lang.reflect.Type
import kotlin.reflect.KClass

/**
 * A builder of an element that can have [functions][FunSpec], [properties][PropertySpec],
 * and [types][TypeSpec].
 *
 * This includes [FileSpecBuilder] and [TypeSpecBuilder]
 */
public interface WithMembersBuilder {
    public fun addFunction(funSpec: FunSpec)
    public fun addProperty(propertySpec: PropertySpec)
    public fun addType(typeSpec: TypeSpec)

    public fun addFunctions(funSpecs: Iterable<FunSpec>) {
        funSpecs.forEach { addFunction(it) }
    }

    public fun addProperties(propertySpecs: Iterable<PropertySpec>) {
        propertySpecs.forEach { addProperty(it) }
    }

    public fun addTypes(typeSpecs: Iterable<TypeSpec>) {
        typeSpecs.forEach { addType(it) }
    }
}

public fun WithMembersBuilder.addProperty(name: String, type: TypeName, vararg modifiers: KModifier): Unit =
    addProperty(property(name, type, *modifiers))

public fun WithMembersBuilder.addProperty(name: String, type: Type, vararg modifiers: KModifier): Unit =
    addProperty(name, type.asTypeName(), *modifiers)

public fun WithMembersBuilder.addProperty(name: String, type: KClass<*>, vararg modifiers: KModifier): Unit =
    addProperty(name, type.asTypeName(), *modifiers)

public fun WithMembersBuilder.addProperty(name: String, type: TypeName, modifiers: Iterable<KModifier>): Unit =
    addProperty(property(name, type, modifiers))

public fun WithMembersBuilder.addProperty(name: String, type: Type, modifiers: Iterable<KModifier>): Unit =
    addProperty(name, type.asTypeName(), modifiers)

public fun WithMembersBuilder.addProperty(name: String, type: KClass<*>, modifiers: Iterable<KModifier>): Unit =
    addProperty(name, type.asTypeName(), modifiers)
