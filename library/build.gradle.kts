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

    applyDefaultHierarchyTemplate()

    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
