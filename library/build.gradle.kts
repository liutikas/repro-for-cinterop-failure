plugins {
    alias(libs.plugins.kotlinMultiplatform)
}

group = "io.github.kotlin"
version = "1.0.0"

kotlin {
    jvm()
    linuxX64 {
        compilations.getByName("main") {
            cinterops {
                create("sqlite3") {
                    includeDirs("sqlite")
                }
            }
        }
    }

    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        nativeMain {
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
        }
    }
}
