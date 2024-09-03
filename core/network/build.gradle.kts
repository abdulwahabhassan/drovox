plugins {
    id("com.drovox.convention.core")
}

android {
    namespace = "com.drovox.core.network"
}


dependencies {
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
}
