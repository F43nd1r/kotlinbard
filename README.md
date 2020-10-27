# KotlinBard

KotlinBard is a collection of extension functions for [KotlinPoet](https://github.com/square/kotlinpoet) to provide a
fluent kotlin DSL for kotlin code generation.

### Example

KotlinPoet's first example is generated with the following:

```kotlin
val file = buildFile("", "HelloWorld") {
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
file.writeTo(System.out)
```

Fun fact: to cover all of KotlinPoet's functions, KotlinBard uses code generation to generate many extension functions
-- it uses a previous version of itself to generate itself!

## Features

### `buildXXX {}` and `addXXX {}` functions for every builder

```kotlin
val file = buildFile {
    addClass(...){ ... }
    addInterface(...){ ... } //Each variant of TypeSpec and FunSpec have separate functions
    addTypeAlias(...){ ... }
}
val annotation = buildAnnotation(...){ ... } //AnnotationSpec
val annotationType = buildAnnotationClass(...){ ... } //TypeSpec that is an annotation class

//Each type of Spec has its own function
val enum = buildEnum(...){ ... }
val intf = buildInterface(...){ ... }
val constructor = buildContructor {}
val getter = buildGetter {}
```

### `modify` extensions for every Spec, corresponding to converting to a builder then applying actions

```kotlin
val newClass = klass.modify(name = newName) { ... }
```

### CodeBlock creation functions

```
val aBlock = codeBlock("println(%S)", "Hello, weird world")
//or, for a more inline syntax:
val aBlock = "println(%S)".codeFmt("Hello, weird world")
```

### Builders are marked with DslMarker

```kotlin
val file = buildFile("", "File") {
    addImport(String::class)
    addClass("Foo") {
        addImport(Int::class) //compile error
    }
}
```

### Extensions and DSLs for control flow

```kotlin
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
```

This generates the following:

```kotlin
fun analyzeTaco() {
    taco.let {
        println(it)
    }
    if (taco.isSpicy()) {
        println("spicy!!")
    } else if (me.isHungry) {
        eat(taco)
    } else {
        saveForLater(taco)
    }
    do {
        makeTaco()
    } while (tacos < 5)
    for (taco in tacos) {
        println("""taco information: $taco""")
    }
    when (taco) {
        is SpicyTaco -> {
            println("Spicy!!")
        }
        else -> eat(taco)
    }
}
```

### Quick property setter with parameter

```kotlin
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
```

### Extensions for `TypeName`s

```kotlin
val myTypeVarName = TypeVariableName("T")
    .reified
    .plusTag(ATag::class, myTag)
val myTypeDecName = Int::class.asTypeName()
    .plusAnnotations(annotationSpec)
```

## Usage

With gradle:

```groovy
repositories {
    jcenter()
}

dependencies {
    implementation("io.github.enjoydambience:kotlinbard:0.3.0")
}
```

----

### lKotlinBard is open source!

If you have feedback or suggestions, feel free to open an issue or submit a pull request.
