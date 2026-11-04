import jetbrains.buildServer.configs.kotlin.*

// Two helper libraries shipped as jars on the DSL compile classpath (.teamcity/.lib/).
// Both builds are broken, which is what reproduces TW-105472:
//   greeter-1.0.jar -> EMPTY (0 bytes)         -> ZipException: zip file is empty
//   metrics-1.0.jar -> CORRUPTED (truncated)   -> ZipException: zip END header not found
// KotlinRunner.getCompilationLibs opens every .lib jar with `new ZipFile(...)` while
// preparing the compile classpath. The first unreadable jar throws and (pre-fix) the
// whole generation aborts. On this server's filesystem greeter-1.0.jar is scanned first,
// so the failure surfaced is "zip file is empty".
import com.example.greeter.Greeter
import com.example.metrics.Metrics

version = "2026.1"

project {

    buildType(Build1)
}

object Build1 : BuildType({
    name = "Build1"

    // Real use of BOTH library jars from .teamcity/.lib/.
    params {
        param("ci.greeting", Greeter.hello("Project5"))
        param("ci.metrics.label", Metrics.label())
    }

    vcs {
        root(DslContext.settingsRoot)
    }
})
