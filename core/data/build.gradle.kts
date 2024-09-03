plugins {
    id("com.drovox.convention.core")
}

android {
    namespace = "com.drovox.core.data"
}

dependencies {
    api(project(":core:model"))
    implementation(project(":core:network"))
    api(project(":core:database"))
    implementation(libs.retrofit.core)
    implementation(libs.androidx.dataStore.core)
    implementation(libs.moshi.kotlin)
    implementation(libs.converter.moshi)
    implementation(libs.gson)
    implementation(libs.play.services.basement)
}
