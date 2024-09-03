package com.drovox.buildlogic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

fun Project.configureDesign(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
    commonExtension.apply {
        buildFeatures {
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = libs.findVersion("androidxComposeCompiler").get().toString()
        }
        dependencies {
            groupedDesignDependencies(libs)
        }
    }
}

internal fun DependencyHandler.groupedDesignDependencies(libs: VersionCatalog) {
    add("api", libs.findLibrary("androidx.core.ktx").get())
    add("api", libs.findLibrary("androidx.appcompat").get())
    add("implementation", libs.findLibrary("google.android.material").get())
    add("api", libs.findLibrary("androidx.core.splashscreen").get())
    add("api", libs.findLibrary("androidx.lifecycle.viewModel.ktx").get())
    groupedComposeDependencies(libs)
}

private fun DependencyHandler.groupedComposeDependencies(libs: VersionCatalog) {
    val bom = libs.findLibrary("androidx-compose-bom").get()
    add("api", platform(bom))
    add("androidTestImplementation", platform(bom))
    add("api", libs.findLibrary("androidx.activity.compose").get())
    add("api", libs.findLibrary("androidx.compose.foundation").get())
    add("api", libs.findLibrary("androidx.compose.foundation.layout").get())
    add("api", libs.findLibrary("androidx.compose.material.iconsExtended").get())
    add("api", libs.findLibrary("androidx.compose.material3").get())
    add("api", libs.findLibrary("androidx.compose.runtime").get())
    add("api", libs.findLibrary("androidx.compose.ui.tooling.preview").get())
    add("api", libs.findLibrary("androidx.compose.ui.util").get())
    add("debugImplementation", libs.findLibrary("androidx.compose.ui.tooling").get())
    add("implementation", libs.findLibrary("androidx.navigation.compose").get())
    add("api", libs.findLibrary("androidx.hilt.navigation.compose").get())
    add("api", libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
    add("api", libs.findLibrary("androidx.lifecycle.viewModelCompose").get())
    add("api", libs.findLibrary("accompanist.systemuicontroller").get())
    add("implementation", libs.findLibrary("coil.kt.compose").get())
}
