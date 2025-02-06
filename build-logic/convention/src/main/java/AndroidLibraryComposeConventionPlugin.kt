import com.android.build.api.dsl.LibraryExtension
import com.example.convention.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

// No.4
/* Library plugin with compose plugin */
class AndroidLibraryComposeConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run{
            pluginManager.run {
                apply("runtracker.android.library")
            }

            extensions.configure<LibraryExtension>{
                configureAndroidCompose(this)
            }
//            or
//            val extension = extensions.getByType<LibraryExtension>()
//            configureAndroidCompose(commonExtension = extension)

        }
    }
}