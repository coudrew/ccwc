package ca.coudrew.ccwc

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.nio.file.Paths
import kotlin.io.path.pathString

class WcOperationsTest {
    private val sourcePath = Paths.get("test.txt").toAbsolutePath().pathString
    private val countArgs = CcwcArgs(count = true, source = sourcePath)
    private val lineArgs = CcwcArgs(lines = true, source = sourcePath)
    private val wordArgs = CcwcArgs(words = true, source = sourcePath)
    private val charArgs = CcwcArgs(characters = true, source = sourcePath)

    @Test
    fun buildOutputCount() {
        val wcOperations = WcOperations(countArgs)
        val actual = wcOperations.buildOutput()
        val expected = "8729 $sourcePath"

        assertEquals(expected, actual)
    }

    @Test
    fun buildOutputLines() {
        val wcOperations = WcOperations(lineArgs)
        val actual = wcOperations.buildOutput()
        val expected = "90 $sourcePath"

        assertEquals(expected, actual)
    }

    @Test
    fun buildOutputWords() {
        val wcOperations = WcOperations(wordArgs)
        val actual = wcOperations.buildOutput()
        val expected = "1275 $sourcePath"

        assertEquals(expected, actual)
    }

    @Test
    fun buildOutputChars() {
        val wcOperations = WcOperations(charArgs)
        val actual = wcOperations.buildOutput()
        val expected = "8729 $sourcePath"

        assertEquals(expected, actual)
    }
}