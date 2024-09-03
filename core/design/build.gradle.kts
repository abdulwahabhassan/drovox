import com.drovox.buildlogic.configureDesign

plugins {
    id("com.drovox.convention.core")
}

android {
    namespace = "com.drovox.core.design"
    configureDesign(this)
}
