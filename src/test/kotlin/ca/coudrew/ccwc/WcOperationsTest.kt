package ca.coudrew.ccwc

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.nio.file.Paths
import kotlin.io.path.pathString

class WcOperationsTest {
    val sourcePath = Paths.get("test.txt").toAbsolutePath().pathString
    val countArgs = CcwcArgs(count = true, source = sourcePath)
    @Test
    fun buildOutputCount() {
        println(sourcePath)
        val wcOperations = WcOperations(countArgs)
        val actual = wcOperations.buildOutput()
        val expected = "342190"

        assertEquals(expected, actual)
    }
}