import com.drovox.buildlogic.configureDesign

plugins {
    id("com.drovox.convention.core")
}

android {
    namespace = "com.drovox.core.ui"
    configureDesign(this)
}

dependencies {
    api(project(":core:design"))
    implementation(project(":core:model"))
    api(libs.accompanist.permissions)
}
