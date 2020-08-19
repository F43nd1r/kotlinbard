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

@file:Suppress("FunctionName", "PropertyName")

package io.github.enjoydambience.kotlinbard

/**
 * Adds an if-statement control flow.
 *
 * After the body, the if statement can be continued with [Else][IfEnd.Else] or [ElseIf][IfEnd.ElseIf].
 */
public inline fun CodeBuildingScope.If(argument: String, vararg args: Any, body: CodeBuildingScope.() -> Unit): IfEnd {
    controlFlow("if ($argument)", *args, body = body)
    return IfEnd(this)
}

/**
 * Represents the end of an if-statement.
 *
 * This can be continued with [ElseIf] and [Else].
 */
public inline class IfEnd(@PublishedApi internal val builder: CodeBuildingScope) {
    /**
     * Adds an `else if` control flow after the previous `if` body.
     *
     * @return an [IfEnd] so the body can be continued.
     */
    public inline fun ElseIf(argument: String, vararg args: Any, body: CodeBuildingScope.() -> Unit): IfEnd = apply {
        builder.controlFlow("else if ($argument)", *args, body = body)
    }

    /**
     * Adds an `else` control flow after the previous `if` body.
     *
     * This ends the if statement.
     */
    public inline infix fun Else(body: CodeBuildingScope.() -> Unit) {
        builder.controlFlow("else", body = body)
    }

    /**
     * Adds a `else xxx` statement after the previous `if` body.
     *
     * This ends the if statement.
     */
    public infix fun Else(codeBlock: CodeBlock) {
        builder.addCode(codeBlock)
    }

    public fun Else(format: String, vararg args: Any) {
        builder.addStatement("else $format", *args)
    }

}

/**
 * Adds a while-statement control flow.
 */
public inline fun CodeBuildingScope.While(argument: String, vararg args: Any, body: CodeBuildingScope.() -> Unit) {
    controlFlow("while ($argument)", *args, body = body)
}

/**
 * Adds a do-while-statement control flow.
 *
 * **The generated code will be incorrect unless the do statement is closed by a [While][DoEnd.While].**
 */
public inline fun CodeBuildingScope.Do(body: CodeBuildingScope.() -> Unit): DoEnd {
    beginControlFlow("do")
    body()
    return DoEnd(this)
}

/**
 * Represents the end of an do control flow.
 *
 * The if statement must be continued with [While]
 */
public inline class DoEnd(private val builder: CodeBuildingScope) {
    /**
     * Adds a `while`, completing the do-while statement.
     */
    public fun While(format: String, vararg args: Any) {
        builder.unindent()
        builder.addCode("} while ($format)\n", *args)
    }
}


/**
 * Adds a for-statement control flow.
 */
public inline fun CodeBuildingScope.For(format: String, vararg args: Any, body: CodeBuildingScope.() -> Unit) {
    controlFlow("for ($format)", *args, body = body)
}

/**
 * Adds a when-statement control flow, without an argument.
 */
public inline fun CodeBuildingScope.When(body: WhenScope.() -> Unit) {
    controlFlow("when") {
        WhenScope(this).body()
    }
}

/**
 * Adds a when-statement control flow with an argument.
 */
public inline fun CodeBuildingScope.When(argument: String, vararg args: Any, body: WhenScope.() -> Unit) {
    controlFlow("when ($argument)", *args) {
        WhenScope(this).body()
    }
}

public inline class WhenScope(@PublishedApi internal val builder: CodeBuildingScope) {

    /** Specifies a when branch with block body. */
    public inline operator fun CodeBlock.minus(body: CodeBuildingScope.() -> Unit) {
        builder.controlFlow("%L ->", this, body = body)
    }

    /** Specifies a when branch with expression body. */
    public operator fun CodeBlock.minus(codeBlock: CodeBlock) {
        builder.addStatement("%L -> %L", this, codeBlock)
    }

    /** Specifies a when branch with expression body. */
    public operator fun CodeBlock.minus(code: String) {
        builder.addStatement("%L -> %L", this, code)
    }

    /** Specifies a when branch with block body. */
    public inline operator fun String.minus(body: CodeBuildingScope.() -> Unit) {
        builder.controlFlow("%L ->", this, body = body)
    }

    /** Specifies a when branch with expression body. */
    public operator fun String.minus(codeBlock: CodeBlock) {
        builder.addStatement("%L -> %L", this, codeBlock)
    }

    /** Specifies a when branch with expression body. */
    public operator fun String.minus(code: String) {
        builder.addStatement("%L -> %L", this, code)
    }
}
