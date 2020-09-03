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

package io.github.enjoydambience.kotlinbard.codegen.generators

import com.squareup.kotlinpoet.CodeBlock
import io.github.enjoydambience.kotlinbard.codegen.SpecInfo
import io.kotest.core.spec.style.FreeSpec
import io.kotest.core.spec.style.scopes.FreeScope
import io.kotest.core.test.TestContext
import io.kotest.matchers.collections.shouldContainAll

/**
 * tests that all applicable specs functions have a corresponding generated function
 */
class ExistenceTest : FreeSpec({
    "SpecBuilders" - {
        testAllSpecs { spec ->
            val allDelegates = SpecBuilders.delegatesFrom(spec)
                .mapTo(mutableSetOf()) { it.name }
            val usedDelegates = SpecBuilders.allGroups[spec]?.map { it.delegateFunName }.orEmpty()

            usedDelegates shouldContainAll allDelegates
        }
    }
    "SpecAdders" - {
        val excludes = mapOf(SpecInfo.of(CodeBlock::class)!! to setOf("add"))
        testAllSpecs { spec ->
            val allAdders = SpecAdders.addersFrom(spec).keys - excludes[spec].orEmpty()
            val usedAdders = SpecAdders.allGroups[spec]?.map { it.delegateFunName }.orEmpty()

            usedAdders shouldContainAll allAdders
        }
    }
})

private suspend inline fun FreeScope.testAllSpecs(crossinline block: TestContext.(SpecInfo) -> Unit) {
    SpecInfo.allSpecs.forEach { spec ->
        "for ${spec.name}" {
            block(spec)
        }
    }
}
