
import com.example.convention.configureKotlinJvm
import org.gradle.api.Plugin
import org.gradle.api.Project

// No.7
/* Domain library module plugin */
class JvmLibraryConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run{
            pluginManager.apply("org.jetbrains.kotlin.jvm")

            configureKotlinJvm() // Replace Java{} section in domain gradle
        }
    }
}