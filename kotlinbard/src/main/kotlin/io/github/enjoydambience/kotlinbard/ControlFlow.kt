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

/**
 * Adds an if-statement control flow.
 *
 * This can be continued with [else][IfEnd.else] or [else if][IfEnd.`else if`].
 */
public inline fun CodeBuildingScope.`if`(
    argument: String,
    vararg args: Any,
    body: CodeBuildingScope.() -> Unit,
): IfEnd {
    controlFlow("if ($argument)", *args, body = body)
    return IfEnd(this)
}

/**
 * Adds an if-statement control flow.
 *
 * After the body, the if statement can be continued with [Else][IfEnd] or [ElseIf][IfEnd].
 */
public inline fun CodeBuildingScope.`if`(
    argument: CodeBlock,
    body: CodeBuildingScope.() -> Unit,
): IfEnd {
    controlFlow("if (%L)", argument, body = body)
    return IfEnd(this)
}

/**
 * Represents the end of an if-statement.
 */
public class IfEnd(@PublishedApi internal val builder: CodeBuildingScope) {
    /**
     * Adds an `else if` block.
     *
     * An else/if chain can still be continued after this.
     */
    @Suppress("FunctionName")
    public inline fun `else if`(argument: String, vararg args: Any, body: CodeBuildingScope.() -> Unit): IfEnd = apply {
        builder.controlFlow("else if ($argument)", *args, body = body)
    }

    /**
     * Adds an `else if` block.
     *
     * An else/if chain can still be continued after this.
     */
    @Suppress("FunctionName")
    public inline fun `else if`(argument: CodeBlock, body: CodeBuildingScope.() -> Unit): IfEnd = apply {
        builder.controlFlow("else if (%L)", argument, body = body)
    }

    /** Adds an else block. */
    public inline infix fun `else`(body: CodeBuildingScope.() -> Unit) {
        builder.controlFlow("else", body = body)
    }

    /** Adds a else statement. */
    public fun `else`(format: String, vararg args: Any) {
        builder.addStatement("else $format", *args)
    }

    /** Adds an else statement. */
    public infix fun `else`(code: String) {
        builder.addStatement("else %L", code)
    }

    /** Adds an else statement. */
    public infix fun `else`(codeBlock: CodeBlock) {
        builder.addStatement("else %L", codeBlock)
    }

}

/**
 * Adds a while control flow.
 */
public inline fun CodeBuildingScope.`while`(argument: String, vararg args: Any, body: CodeBuildingScope.() -> Unit) {
    controlFlow("while ($argument)", *args, body = body)
}

/**
 * Adds a while control flow.
 */
public inline fun CodeBuildingScope.`while`(argument: CodeBlock, body: CodeBuildingScope.() -> Unit) {
    controlFlow("while (%L)", argument, body = body)
}


/**
 * Adds a do-while control flow.
 *
 * **The generated code will be incorrect unless completed with [while][DoEnd.while].**
 */
public inline fun CodeBuildingScope.`do`(body: CodeBuildingScope.() -> Unit): DoEnd {
    beginControlFlow("do")
    body()
    return DoEnd(this)
}

/**
 * Represents the end of an do-while; [while] must be used to generate correct code.
 */
public class DoEnd(private val builder: CodeBuildingScope) {
    public fun `while`(argument: String, vararg args: Any) {
        builder.unindent()
        builder.addCode("} while ($argument)\n", *args)
    }

    public infix fun `while`(argument: String) {
        builder.unindent()
        builder.addCode("} while (%L)\n", argument)
    }

    public infix fun `while`(codeBlock: CodeBlock) {
        builder.unindent()
        builder.addCode("} while (%L)\n", codeBlock)
    }
}


/**
 * Adds a `for` control flow.
 */
public inline fun CodeBuildingScope.`for`(format: String, vararg args: Any, body: CodeBuildingScope.() -> Unit) {
    controlFlow("for ($format)", *args, body = body)
}

/**
 * Adds a `for` control flow.
 */
public inline fun CodeBuildingScope.`for`(codeBlock: CodeBlock, body: CodeBuildingScope.() -> Unit) {
    controlFlow("for (%L)", codeBlock, body = body)
}

/**
 * Adds a `when` control flow, without an argument.
 */
public inline fun CodeBuildingScope.`when`(body: WhenBody.() -> Unit) {
    controlFlow("when") {
        WhenBody(this).body()
    }
}

/**
 * Adds a `when` control flow, with an argument.
 */
public inline fun CodeBuildingScope.`when`(argument: String, vararg args: Any, body: WhenBody.() -> Unit) {
    controlFlow("when ($argument)", *args) {
        WhenBody(this).body()
    }
}

/**
 * Adds a `when` control flow, with an argument.
 */
public inline fun CodeBuildingScope.`when`(argument: CodeBlock, body: WhenBody.() -> Unit) {
    controlFlow("when (%L)", argument) {
        WhenBody(this).body()
    }
}

public class WhenBody(@PublishedApi internal val builder: CodeBuildingScope) {

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

    /** Specifies a when branch with block body. */
    public inline fun case(code: CodeBlock, body: CodeBuildingScope.() -> Unit) {
        builder.controlFlow("%L ->", code, body = body)
    }

    /** Specifies a when branch with block body. */
    public inline fun case(format: String, vararg args: Any, body: CodeBuildingScope.() -> Unit) {
        builder.controlFlow("$format ->", *args, body = body)
    }

}
