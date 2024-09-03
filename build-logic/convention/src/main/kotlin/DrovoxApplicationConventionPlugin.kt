import com.android.build.api.dsl.ApplicationExtension
import com.drovox.buildlogic.configureDesign
import com.drovox.buildlogic.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.project

class DrovoxApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("com.android.application")
                apply("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")

            }
            extensions.configure<ApplicationExtension> {
                defaultConfig.targetSdk = 34
                configureKotlinAndroid(this)
                configureDesign(this)
            }
            dependencies {
                add("implementation", project(":core:ui"))
                add("implementation", project(":core:domain"))
                add("implementation", project(":feature:map"))
            }
        }
    }
}