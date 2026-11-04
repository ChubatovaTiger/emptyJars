import jetbrains.buildServer.configs.kotlin.*
import com.example.ciutils.BuildDefaults

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2026.1"

project {

    buildType(Build1)
}

object Build1 : BuildType({
    name = "Build1"

    // Real use of the library shipped in .teamcity/.lib/ciutils-1.0.jar.
    // If that jar (or any other jar in .lib/) is empty/corrupted, the whole
    // Kotlin DSL generation aborts with java.util.zip.ZipException (TW-105472).
    params {
        param("ci.build.label", BuildDefaults.buildLabel("Project5"))
        param("ci.timeout.minutes", BuildDefaults.DEFAULT_TIMEOUT_MINUTES.toString())
    }

    vcs {
        root(DslContext.settingsRoot)
    }
})
