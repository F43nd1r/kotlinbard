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

plugins {
    alias(libs.plugins.kotlin)
    `java-library`

    idea
    alias(libs.plugins.dokka)
    `maven-publish`
    signing
}

dependencies {
    api(libs.kotlinpoet)

    testImplementation(libs.bundles.kotest)
}

kotlin {
    explicitApiWarning()
}

// codegen
val generatedSrc = "$buildDir/generated-src"
val codegenProject = project("codegen")

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

tasks.dokkaHtml {
    outputDirectory.set(buildDir.resolve("javadoc"))
}
val dokkaJar by tasks.creating(Jar::class) {
    group = "documentation"
    archiveClassifier.set("javadoc")
    from(tasks.dokkaHtml)
}
val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}
publishing {
    publications {
        create<MavenPublication>("default") {
            from(components["java"])
            artifact(dokkaJar)
            artifact(sourcesJar)
            artifactId = "kotlinbard"
            pom {
                name.set("KotlinBard")
                url.set("https://github.com/F43nd1r/kotlinbard")
                description.set("KotlinBard is a collection of extension functions for KotlinPoet to provide a fluent kotlin DSL for code generation.")
                licenses {
                    license {
                        name.set("Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                        distribution.set("repo")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/F43nd1r/kotlinbard.git")
                    developerConnection.set("scm:git:https://github.com/F43nd1r/kotlinbard.git")
                    url.set("https://github.com/F43nd1r/kotlinbard.git")
                }
                developers {
                    developer {
                        id.set("enjoydambience")
                        name.set("enjoydambience")
                    }
                    developer {
                        id.set("f43nd1r")
                        name.set("Lukas Morawietz")
                    }
                }
            }
        }
    }
}

signing {
    val signingKey = project.findProperty("signingKey") as? String ?: System.getenv("SIGNING_KEY")
    val signingPassword = project.findProperty("signingPassword") as? String ?: System.getenv("SIGNING_PASSWORD")
    useInMemoryPgpKeys(signingKey, signingPassword)
    sign(publishing.publications["default"])
}

tasks.publish { dependsOn(tasks.check) }

// codegen

fun configureCodegenTask() {
    val codegen by tasks.creating(JavaExec::class) {
        group = "build"
        description = "Runs codegen"

        dependsOn(codegenProject.tasks.assemble)

        inputs.files(codegenProject.tasks.jar.get().outputs.files)
        outputs.dir(generatedSrc)

        doFirst {
            delete(generatedSrc)
        }

        classpath = codegenProject.sourceSets.main.get().runtimeClasspath
        mainClass.set(codegenProject.ext["mainClass"] as String)
        args(generatedSrc)
    }
    tasks.compileKotlin.configure { dependsOn(codegen) }
    tasks.test.configure { dependsOn(codegenProject.tasks.test) }
    tasks.dokkaHtml.configure { dependsOn(codegen) }
    sourcesJar.dependsOn(codegen)
}

codegenProject.afterEvaluate {
    configureCodegenTask()
}
