pluginManagement {
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith("br.dev.pedrolamarao.metal.")) {
                useModule("br.dev.pedrolamarao.gradle.metal:plugins:0.5")
            }
        }
    }
}

rootProject.name = "gradle-metal-sqlite"