import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension


val GROUP: String by project
group = GROUP
version = "0.0.1"


plugins {
    kotlin("jvm")
    idea

    id("org.jetbrains.dokka") version "0.10.1"
    `maven-publish`
}

dependencies {
    implementation(kotlin("stdlib-jdk8", version = Versions.Kotlin.stdlib))
    compileOnly(Deps.kotlinPoet)

    testImplementation(Deps.kotlinPoet)
    testImplementation(Deps.Test.jUnit)
    testImplementation(Deps.Test.Kotest.runner)
    testImplementation(Deps.Test.Kotest.assertions)
    testImplementation(Deps.Test.Kotest.console)
}

pluginManager.withPlugin("org.jetbrains.kotlin.jvm") {
    configure<KotlinProjectExtension> {
        explicitApiWarning()
    }
}
// codegen
val generatedSrc = "$buildDir/generated-src"
val codegenProject = project(":codegen")
val codegen: Task by tasks.creating {
    group = "build"
    description = "Runs codegen"

    dependsOn(codegenProject.tasks.build)

    //redo codegen when codegen compilation changes
    inputs.dir(codegenProject.buildDir.resolve("libs"))
    outputs.dir(generatedSrc)

    doFirst {
        delete(generatedSrc)
        javaexec {
            classpath = codegenProject.sourceSets.main.get().runtimeClasspath
            main = codegenProject.ext["mainClass"] as String
            args(generatedSrc)
        }
    }
}
tasks.classes { dependsOn(codegen) }

sourceSets.main {
    java {
        srcDirs(generatedSrc)
    }
}
idea {
    module {
        generatedSourceDirs.add(file(generatedSrc))
    }
}

// publish

// group and version defined at top of file
tasks.dokka {
    outputFormat = "html"
    outputDirectory = "$buildDir/javadoc"
}
val dokkaJar by tasks.creating(Jar::class) {
    group = "documentation"
    description = "Assembles Kotlin docs with Dokka"
    archiveClassifier.set("javadoc")
    from(tasks.dokka)
}
val sourcesJar by tasks.creating(Jar::class) {
    description = "Assembles sources"
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}
publishing {
    publications {
        create<MavenPublication>("minimal") {
            from(components["java"])
            artifact(sourcesJar)
        }
        create<MavenPublication>("default") {
            from(components["java"])
            artifact(dokkaJar)
            artifact(sourcesJar)

        }
    }
    repositories {
        maven {
            name = "bootstrap"
            url = uri("$rootDir/bootstrapMavenRepo")
        }
    }
}
tasks.create("publishToBoostrapRepository") {
    group = "bootstrap"
    description = "Publishes to the local bootstrap repository to be used by the codegen module"
    dependsOn("publishMinimalPublicationToBootstrapRepository")
}
