pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()

        maven("https://dl.bintray.com/kotlin/kotlin-eap")
        maven("https://plugins.gradle.org/m2/")
    }
}
rootProject.name = "kotlinbard"

include("codegen")
//includeBuild("xbuildSrc")

include("kotlinbard")
//include("codegenOutPreview")
