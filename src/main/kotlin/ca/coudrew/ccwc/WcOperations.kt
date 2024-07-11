package ca.coudrew.ccwc

import java.io.BufferedReader
import java.io.File
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.attribute.BasicFileAttributes
import java.util.regex.Pattern
import kotlin.io.path.writeLines

class WcOperations(private val args: CcwcArgs) {
    private val filePath: Path = Paths.get(args.source)
    private var response: String = ""
    private var fileAsLines: MutableList<String> = mutableListOf()

    constructor(args: CcwcArgs, inputReaderLines: MutableList<String>) : this(args) {
        this.fileAsLines = inputReaderLines
    }

    private fun createTempFileFromLines(lines: MutableList<String>): Path {
        val tempFile = kotlin.io.path.createTempFile("kotlinTemp", "tmp")
        tempFile.writeLines(lines)
        tempFile.toFile().deleteOnExit()

        return tempFile
    }

    private fun getByteCount(): Long {
        var fileSize: Long = 0
        var path: Path = filePath
        if (this.fileAsLines.size > 0) {
            path = createTempFileFromLines(this.fileAsLines)
        }
        if (Files.exists(path)) {
            fileSize = Files.size(path)
        }
        return fileSize
    }

    private fun getLineCount(): Long {
        var lineCount: Long = 0
        if (this.fileAsLines.size > 0) {
            lineCount = this.fileAsLines.size.toLong()
        } else if (Files.exists(filePath)) {
            lineCount = Files.readAllLines(filePath).size.toLong()
        }
        return lineCount
    }

    private fun getWordCount(): Long {
        var wordCount: Long = 0
        var file: String = ""

        if (this.fileAsLines.size > 0) {
            file = this.fileAsLines.joinToString(" ")
        } else if (Files.exists(filePath)) {
            file = Files.readAllLines(filePath).joinToString(separator = " ")
        }

        val wordsList: List<String> = file.split(Regex("\\s+")).filter { it.isNotBlank() }
        wordCount = wordsList.size.toLong()

        return wordCount
    }

    private fun getCharCount(): Long {
        var charCount: Long = 0
        val path = if (this.fileAsLines.size > 0) this.createTempFileFromLines(this.fileAsLines) else filePath
        if (Files.exists(path)) {
            val bufferedReader = Files.newBufferedReader(filePath);
            while (bufferedReader.read() != -1) {
                charCount++
            }
        }
        return charCount
    }

    private fun appendToResponse(func: () -> Long): String {
        return if (this.response.isNotEmpty()) "${this.response} ${func()}" else "${func()}"
    }

    fun buildOutput(): String {
        if (args.count) {
            this.response = appendToResponse(this::getByteCount)
        }
        if (args.lines) {
            this.response = appendToResponse(this::getLineCount)
        }
        if (args.words) {
            this.response = appendToResponse(this::getWordCount)
        }
        if (args.characters) {
            this.response = appendToResponse(this::getCharCount)
        }

        val output = if (args.source.isNotEmpty()) "${this.response} ${args.source}" else this.response

        return output
    }
}