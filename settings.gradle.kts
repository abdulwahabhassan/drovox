pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "Drovox"

/**
 * app module
 */
include(":app")

/**
 * core modules
 */
include(":core:data")
include(":core:design")
include(":core:domain")
include(":core:model")
include(":core:network")
include(":core:database")
include(":core:ui")

/**
 * feature modules
 */
include(":feature:map")