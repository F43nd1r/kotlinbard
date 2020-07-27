plugins {
    kotlin("jvm")
}
repositories {
    maven("$rootDir/bootstrapMavenRepo")
}

dependencies {
    implementation(kotlin("stdlib-jdk8", version = Versions.Kotlin.stdlib))
    implementation(kotlin("reflect", version = Versions.Kotlin.stdlib))
    implementation(Deps.kotlinPoet)
    implementation(Deps.kaseChange)
    implementation("io.github.enjoydambience:kotlinbard:+")

    testImplementation(Deps.Test.jUnit)
    testImplementation(Deps.Test.Kotest.runner)
    testImplementation(Deps.Test.Kotest.assertions)
    testImplementation(Deps.Test.Kotest.console)
}

val mainClass by ext("io.github.enjoydambience.kotlinbard.codegen.MainKt")
