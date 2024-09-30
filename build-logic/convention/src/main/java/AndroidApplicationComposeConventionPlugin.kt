import com.android.build.api.dsl.ApplicationExtension
import com.example.convention.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

// No.2
/* Application plugin with compose plugin */
class AndroidApplicationComposeConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run{

            pluginManager.apply("runtracker.android.application")

            extensions.configure<ApplicationExtension>{
                configureAndroidCompose(this)
            }
//            or
//            val extension = extensions.getByType<ApplicationExtension>()
//            configureAndroidCompose(commonExtension = extension)
        }
    }

}