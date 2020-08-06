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

package io.github.enjoydambience.kotlinbard.codegen.generators

import com.squareup.kotlinpoet.FileSpec

/**
 * Generates a file.
 *
 * This interface allows for some common configuration between generated files.
 */
interface FileGenerator {
    val fileName: String get() = "_" + javaClass.simpleName
    fun FileSpec.Builder.generate()

    /**
     * Gets the name of the source file that contains this generator.
     *
     * This is used in the "this file is auto generated" comment.
     */
    val generatorSourceFileName: String
        get() = javaClass.simpleName + ".kt"
}


