### KotlinBard
This module contains the main sources for KotlinBard.

Many source files (for extension functions) are done using code generation. The code generation logic is done in the `kotlinbard:codegen` submodule.

Running the `codegen` gradle task will redo code generation. The generated source files are currently placed in `/build/generated-src`. Codegen is also automatically run by gradle before compiling this module.

Some tests are also in the codegen submodule in addition to the tests in this module.
