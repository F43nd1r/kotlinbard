# KotlinBard

KotlinBard is a kotlin dsl to generate kotlin code, built on top of kotlin-poet [KotlinPoet](https://github.com/square/kotlinpoet).


Generate kotlin code like so:
```kotlin
val file = createFile("", "HelloWorld") {
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
```

Or, for control flow:
```kotlin
val body = createCodeBlock {
    If("taco.isSpicy()") {
        addStatement("println(%S)", "spicy!!")
    }.ElseIf("me.isHungry") {
        addStatement("eat(taco)")
    } Else {
        addStatement("saveForLater(taco)")
    }

    Do {
        addStatement("makeTaco()")
    }.While("tacos < 5")

    For("taco in tacos") {
        addStatement("println(%P)", "taco information: \$taco")
    }

    When("taco") {
        e("alicesTaco, bobsTaco") then {
            addStatement("println(%S)", "Don't eat others' tacos!")
        }
        Is(SpicyTaco::class).then("println(%S)", "spicy taco!!")
        nIn("softTacoList") then {
            addStatement("eatSlowly(taco)")
        }
        Else("eat(%L)", "taco")
    }
}
val func = createFunction("tacos") {
    addCode(body)
}
```
This generates the following function:
```kotlin
fun tacos() {
  if (taco.isSpicy()) {
    println(spicy!!)
  }
  else if (me.isHungry) {
    eat(taco)
  }
  else {
    saveForLater(taco)
  }
  do {
    makeTaco()
  } while (tacos < 5)
  for (taco in tacos) {
    println("""taco information: $taco""")
  }
  when (taco) {
    alicesTaco, bobsTaco -> {
      println("Don't eat others' tacos!")
    }
    is io.github.enjoydambience.kotlinbard.SpicyTaco -> println("spicy taco!!")
    !in softTacoList -> {
      eatSlowly(taco)
    }
    else -> eat(taco)
  }
}
```

### Current features:
- `createXXX {}` functions to create specs.
- `addXXX {}` functions within builders
- Control flow

Fun fact: KotlinBard uses code generation to generate many functions -- it uses a previous version of itself to generate itself!


### Usage

Add this to your gradle config:
```groovy
repositories {
    maven {
        url  "https://dl.bintray.com/enjoydambience/maven" 
    }
}

dependencies {
    implementation "io.github.enjoydambience:kotlinbard:0.0.1"
}
```
Kotlin bard is still under development; api is subject to change.

### Roadmap
- Semantically checked modifiers and types
- Additional features to be decided
