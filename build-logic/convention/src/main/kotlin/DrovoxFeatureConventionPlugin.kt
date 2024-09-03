import com.android.build.gradle.LibraryExtension
import com.drovox.buildlogic.configureDesign
import com.drovox.buildlogic.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class DrovoxFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("com.android.library")
            }
            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                configureDesign(this)
            }
            dependencies {
                add("implementation", project(":core:ui"))
                add("implementation", project(":core:domain"))
            }
        }
    }
}
