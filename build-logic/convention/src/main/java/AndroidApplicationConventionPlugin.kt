import com.android.build.api.dsl.ApplicationExtension
import com.example.convention.ExtensionType
import com.example.convention.configureBuildTypes
import com.example.convention.configureKotlinAndroid
import com.example.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

// No.1
/* Application plugin without compose plugin */
class AndroidApplicationConventionPlugin: Plugin<Project> {
    /* <p>This interface is the main API you use to interact with Gradle from your build file. From a <code>Project</code>,
     * you have programmatic access to all of Gradle's features.*/
    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                // Ths section is same as plugin{} section in gradle
                apply("com.android.application")          // replace alias(libs.plugins.android.application)
                apply("org.jetbrains.kotlin.android")     // replace alias(libs.plugins.jetbrains.kotlin.android)
                //apply(libs.findPlugin("android-application").get().toString()) //Wrong
                //apply(libs.findPlugin("android-library").get().toString())     //Wrong
            }

            extensions.configure<ApplicationExtension> {
                // Ths section is same as android{} section in gradle
                defaultConfig{
                    applicationId = libs.findVersion("projectApplicationId").get().toString()
                    targetSdk = libs.findVersion("projectTargetSdkVersion").get().toString().toInt()
                    versionCode = libs.findVersion("projectVersionCode").get().toString().toInt()
                    versionName = libs.findVersion("projectVersionName").get().toString()
                }

                configureKotlinAndroid(this)

                configureBuildTypes(
                    commonExtension = this,
                    extensionType = ExtensionType.APPLICATION
                )
            }
        }
    }

}