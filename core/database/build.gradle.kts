plugins {
    id("com.drovox.convention.core")
}

android {
    namespace = "com.drovox.core.database"
}

dependencies {
    implementation(project(":core:model"))
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    implementation(libs.room.ktx)
    implementation(libs.room.paging)
    implementation(libs.room.testing)
    kapt(libs.room.compiler)
}