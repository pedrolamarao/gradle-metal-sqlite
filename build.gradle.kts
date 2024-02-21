import br.dev.pedrolamarao.gradle.metal.MetalCompile
import de.undercouch.gradle.tasks.download.Download

plugins {
    id("br.dev.pedrolamarao.metal.library")
    id("br.dev.pedrolamarao.metal.c")
    id("de.undercouch.download") version("5.5.0")
}

val prefix = "sqlite-amalgamation-3450100"
val url = "https://sqlite.com/2024/sqlite-amalgamation-3450100.zip"

val download = tasks.register<Download>("download") {
    src(url)
    dest(layout.buildDirectory.dir("zip"))
    overwrite(false)
}

val sources = tasks.register<Copy>("sources") {
    from(download.map{zipTree(it.outputs.files.singleFile)})
    into("src/main/c")
    include("**/*.c")
    eachFile { path = path.removePrefix("${prefix}/") }
    includeEmptyDirs = false
}

val headers = tasks.register<Copy>("headers") {
    from(download.map{zipTree(it.outputs.files.singleFile)})
    into("src/main/cpp")
    include("**/*.h")
    eachFile { path = path.removePrefix("${prefix}/") }
    includeEmptyDirs = false
}

tasks.withType<MetalCompile>().configureEach {
    dependsOn(sources,headers)
}

tasks.wrapper.configure {
    gradleVersion = "8.6"
}