plugins {
    id("com.drovox.convention.core")
}

android {
    namespace = "com.drovox.core.domain"
}

dependencies {
    api(project(":core:data"))
}
