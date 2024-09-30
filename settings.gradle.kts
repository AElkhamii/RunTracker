// pluginManagement used to fetch and download plugins online by using google - mavenCentral - gradlePluginPortal
pluginManagement {
    // Convention Plugins
    // Any thing inside includedBuild module considered as separate project inside our app at least in sense of gradle.
    // that means this build logic module is kind of its own isolated place for own gradle logic we can use.
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

// dependencyResolutionManagement used to fetch and download plugins dependencies by using google - mavenCentral - gradlePluginPortal
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

// Write project name
rootProject.name = "RunTracker"
// makes you include custom modules dependencies easier.
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
// Include all modules in the RunTracker APP
include(":app")
include(":auth:data")
include(":auth:domain")
include(":auth:presentation")
include(":core:presentation:designsystem")
include(":core:presentation:ui")
include(":core:domain")
include(":core:data")
include(":core:database")
include(":run:data")
include(":run:domain")
include(":run:presentation")
include(":run:location")
include(":run:network")
// Remove this because we do not want to include the module here in setting gradle file of our application project
//include(":build-logic:convention")
