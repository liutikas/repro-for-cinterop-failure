package io.github.kotlin.fibonacci

import kotlinx.cinterop.*
import androidx.sqlite3.bundled.*

actual val firstElement: Int = 3
actual val secondElement: Int = 5

@OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)
fun doThings() {
    SQLITE_OPEN_CREATE
}