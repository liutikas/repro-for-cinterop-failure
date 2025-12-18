package io.github.kotlin.fibonacci

import cnames.structs.sqlite3
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.COpaquePointer
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.CPointerVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.allocPointerTo
import kotlinx.cinterop.cstr
import kotlinx.cinterop.get
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.staticCFunction
import kotlinx.cinterop.toCStringArray
import kotlinx.cinterop.toKString
import kotlinx.cinterop.value
import sqlite3.SQLITE_OPEN_CREATE
import sqlite3.SQLITE_OPEN_READWRITE
import sqlite3.sqlite3_close
import sqlite3.sqlite3_exec
import sqlite3.sqlite3_free
import sqlite3.sqlite3_open_v2
import sqlite3.sqlite3_vmprintf
import platform.posix.vprintf
import kotlin.test.Test

@OptIn(ExperimentalForeignApi::class)
class SQLiteTest {
    @Test
    fun `sample test sqlite`() {
        memScoped<Unit> {
            val dbPointer = allocPointerTo<sqlite3>()
            sqlite3_open_v2(
                filename = ":memory:",
                ppDb = dbPointer.ptr,
                flags = SQLITE_OPEN_READWRITE or SQLITE_OPEN_CREATE,
                zVfs = null
            )
            val errorMessage = allocPointerTo<ByteVar>()
            val callback = staticCFunction { _: COpaquePointer?, argc: Int, argv: CPointer<CPointerVar<ByteVar>>?, colNames: CPointer<CPointerVar<ByteVar>>? ->
                for (i in 0 until argc) {
                    val colName = colNames?.get(i)?.toKString() ?: "Unknown"
                    val value = argv?.get(i)?.toKString() ?: "NULL"
                    println("$colName = $value")
                    val spt = sqlite3_vmprintf("$colName = $value", null)
                    sqlite3_free(spt)
                }
                0 // Return 0 to continue execution
            }
            sqlite3_exec(dbPointer.value, "PRAGMA user_version", callback, null, errorMessage.ptr)
            sqlite3_close(dbPointer.value)

            vprintf("%s", null)
        }
    }
}