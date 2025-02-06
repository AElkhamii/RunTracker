
import com.example.convention.addUiLayerDependency
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

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

            // Just add some dependencies to AndroidLibraryComposeConventionPlugin
            dependencies {
                addUiLayerDependency(target)
            }
        }
    }
}