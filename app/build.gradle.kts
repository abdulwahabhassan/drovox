import com.drovox.buildlogic.BuildType
import java.io.FileInputStream
import java.util.Properties
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.io.ByteArrayOutputStream
import java.io.File
import com.android.build.gradle.api.ApplicationVariant
import com.android.build.gradle.api.BaseVariantOutput
import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import java.util.Locale

plugins {
    id("com.drovox.convention.application")
    alias(libs.plugins.org.jetbrains.kotlin.android)
}

val keystorePropertiesFile: File? = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(keystorePropertiesFile?.let { FileInputStream(it) })

android {
    namespace = "com.drovox"
    defaultConfig {
        applicationId = "com.drovox"
        versionCode = 1
        versionName = "1.0.0"
    }

    signingConfigs {
        create("app-signing-config") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
        }
    }
    buildTypes {
        debug {
            applicationIdSuffix = BuildType.DEBUG.applicationIdSuffix
        }
        release {
            isMinifyEnabled = false
            applicationIdSuffix = BuildType.RELEASE.applicationIdSuffix
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            signingConfig = signingConfigs.getByName("app-signing-config")
        }

        android.applicationVariants.all(OutputApkFileNamingAction())
    }
}

class OutputApkFileNamingAction : Action<ApplicationVariant> {
    override fun execute(variant: ApplicationVariant) {
        val fileName = createFileName(variant)
        variant.outputs.all(VariantOutputAction(fileName))
    }

    private fun createFileName(variant: ApplicationVariant): String {
        return "drovox" +
                "-${variant.name}" +
                "-v${variant.versionName}" +
                "-${getBranchName()}" +
                "-${LocalDate.now()}.apk"
    }


    private fun getDateTimeFormat(): String {
        val simpleDateFormat = SimpleDateFormat("dd-MMM-yy")
        return simpleDateFormat.format(LocalDate.now())
    }

    class VariantOutputAction(
        private val fileName: String
    ) : Action<BaseVariantOutput> {
        override fun execute(output: BaseVariantOutput) {
            if (output is BaseVariantOutputImpl) {
                output.outputFileName = fileName
            }
        }
    }
}

fun getBranchName(): String? {
    return try {
        println("Task Getting Branch Name..")
        val stdout = ByteArrayOutputStream()
        project.exec {
            commandLine("git", "rev-parse", "--abbrev-ref", "HEAD")
            standardOutput = stdout
        }
        val branchName = stdout.toString().replace("/", "-").replace("\n", "")
            .lowercase(Locale.getDefault())
        println("Git Current Branch = $branchName")
        branchName
    } catch (e: Exception) {
        println("Exception = ${e.message}")
        null
    }
}
