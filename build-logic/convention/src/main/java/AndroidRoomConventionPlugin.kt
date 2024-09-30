import androidx.room.gradle.RoomExtension
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.example.convention.ExtensionType
import com.example.convention.configureAndroidCompose
import com.example.convention.configureBuildTypes
import com.example.convention.configureKotlinAndroid
import com.example.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

// No.6
/* Room plugin with room dependencies */
class AndroidRoomConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run{
            pluginManager.run {
                apply("androidx.room")           // Room gradle plugin
                apply("com.google.devtools.ksp") // since room relies on code generation, so we need KSP plugin (Kotlin simple processing)
            }

            extensions.configure<RoomExtension>{
                schemaDirectory("$projectDir/schemas")
            }

            dependencies{
                "testImplementation"(libs.findLibrary("room.runtime").get())
                "testImplementation"(libs.findLibrary("room.ktx").get())
                "ksp"(libs.findLibrary("room.compiler").get())
            }
        }
    }
}