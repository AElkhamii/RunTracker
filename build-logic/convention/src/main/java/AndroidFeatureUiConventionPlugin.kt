import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.example.convention.ExtensionType
import com.example.convention.addUiLayerDependency
import com.example.convention.configureAndroidCompose
import com.example.convention.configureBuildTypes
import com.example.convention.configureKotlinAndroid
import com.example.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

// No.5
/* Library plugin with compose plugin
 + Dependencies related to designsystem module and UI module containing utility functions from Core module
 + Dependencies for koin and compose libraries which will be needed in each Ui Feature.
*/
class AndroidFeatureUiConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run{
            pluginManager.run {
                apply("runtracker.android.library.compose")
            }

            dependencies {
                addUiLayerDependency(target)
            }
        }
    }
}