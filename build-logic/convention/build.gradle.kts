import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

gradlePlugin {
    plugins {
        register("drovoxApplicationConvention") {
            id = "com.drovox.convention.application"
            implementationClass = "DrovoxApplicationConventionPlugin"
        }
        register("drovoxCoreConvention") {
            id = "com.drovox.convention.core"
            implementationClass = "DrovoxCoreConventionPlugin"
        }
        register("drovoxFeatureConvention") {
            id = "com.drovox.convention.feature"
            implementationClass = "DrovoxFeatureConventionPlugin"
        }
    }
}
