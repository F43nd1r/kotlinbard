# KotlinBard

KotlinBard is a kotlin dsl for code generation, built on top of [KotlinPoet](https://github.com/square/kotlinpoet).

Publishing coming (very) soon

Current features:
- `createXXX {}` and `addXXX {}` functions to build and add Specs

This allows you to use KotlinPoet like so:
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

Fun fact: KotlinBard uses code generation to generate functions -- it uses a previous version of itself to generate itself!

## Roadmap:
- Code and control flow dsl
- Publish version 0.1.0
- Modifiers dsl
- Additional features to be decided
 
The eventual goal is to be able to write code for code generation that is similar to the output.

(Something similar to the following, syntax not final):
```kotlin
val file = create.file("","HelloEveryone"){
    val className = ClassName("EveryoneGreeter")
    val namesParam = param.private.Val["names"] of type<Iterable<String>>()
    add.public.open.Class[className](
        namesParam,
        "greeting" of type<String>() init code("%S","Hello")
    ) {
        extends<AbstractGreeter>("greeting") //super call

        init {
            """
            require(names.isNotEmpty()){ "%N should not be empty" }
            """.trimIndent().asCode(namesParam)
        }
        val moreModifiers = listOf(final, override)
        add.public(moreModifiers).Fun["greet"](
            "greetingInfo" of GreetingInfo::class
        ).returns<Unit> {
            For("name" In namesParam){
               """
                println("%P")
                """.trimIndent()
                    .asCode("\$greeting, \$name!")
            }
        }
    }
}    
file.writeTo(System.out)
```
which generates
```kotlin
open class EveryoneGreeter(
    private val names: Iterable<String>,
    greeting: String = "Hello"
) : AbstractGreeter(greeting), Greeter {
    init {
        require(names.isNotEmpty()){ "names should not be empty" }
    }
    final override fun greet(greetingInfo: GreetingInfo) {
        for(name in names){
            println("$greeting, $name!")
        }
    }
}

```
