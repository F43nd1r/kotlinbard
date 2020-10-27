# Version 0.4.0

- Kotlin updated to 1.4.10
- KotlinPoet updated to 1.7.2
- Doc improvements

# Version 0.3.0

- Kotlin updated to 1.4.0
- Added more extensions functions to form a complete set (every KotlinPoet builder/adder function now has a counterpart)
- Added extensions for `TypeName`s
- Changed some extension function names to match KotlinPoet counterparts more closely
- Changed to use backtick (escaped) names for control flow dsl instead of capitalized names
- Revised `when` dsl to be more fluent
- Fixed default parameters in `modify` extensions
- Removed parameters DSL
- Removed some codeblock shortcuts

# Version 0.2.0

- Added extension shortcut CodeBlock creators
- Added parameters dsl
- Added SpecModifiers
- Added more add extensions
- Remove need to re-specify type in property setter

# Version 0.1.0

- Disambiguated `annotation` and `annotationClass`
- Added DslMarker for builder scopes
- Added function for parameterless setter
- Added Functions for `CodeBlock`s
- Added `init` blocks
- Added `addConstructor` for secondary constructors
- Added `addEnumConstant`

# Version 0.0.2

- Added control flow dsl

# Version 0.0.1

- Initial release
- Creators and adders for specs
