plugins {
    id("com.drovox.convention.feature")
    alias(libs.plugins.org.jetbrains.kotlin.android)
}

android {
    namespace = "com.drovox.feature.map"
    secrets {
        propertiesFileName = "secrets.properties"
        defaultPropertiesFileName = "local.defaults.properties"
    }
}

dependencies {
    implementation(libs.gms.play.services.location)
    implementation(libs.gms.play.services.maps)
    implementation(libs.com.android.maps.compose)
    implementation(libs.places)
}
