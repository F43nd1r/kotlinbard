Many source files are auto-generated using code generation, from the codegen module.

Run the `codegen` gradle task to redo code generation. This will also run before gradle compiles this module. This will create the generated source files in `/build/generated-src`.

## Bootstrapping
Kotlin bard is partially bootstrapped -- the codegen is made using a previous version of itself.

To avoid many problems with circular dependencies, the codegen module uses a version of itself published a folder maven repository in `bootstrapMavenRepo`. Run the `publishToBootstrapRepository` gradle task to publish the current version into this repo.

*This may be removed when kotlin bard is published to an online repository. Then it will simply use a previous version of itself from the repository.*
