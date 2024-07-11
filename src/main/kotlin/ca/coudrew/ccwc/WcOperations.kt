package ca.coudrew.ccwc

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.writeLines
import kotlin.io.path.createTempFile

class WcOperations(private val args: CcwcArgs) {
    private var filePath: Path = Paths.get(args.source)
    private var response: String = ""

    constructor(args: CcwcArgs, inputReaderLines: MutableList<String>) : this(args) {
        val tempFile = createTempFile("kotlinTemp", "tmp")
        tempFile.writeLines(inputReaderLines)
        tempFile.toFile().deleteOnExit()
        filePath = tempFile
    }

    private fun getByteCount(): Long {
        var fileSize: Long = 0
        if (Files.exists(filePath)) {
            fileSize = Files.size(filePath)
        }
        return fileSize
    }

    private fun getLineCount(): Long {
        var lineCount: Long = 0
        if (Files.exists(filePath)) {
            lineCount = Files.readAllLines(filePath).size.toLong()
        }
        return lineCount
    }

    private fun getWordCount(): Long {
        var wordCount: Long = 0
        var file = ""

        if (Files.exists(filePath)) {
            file = Files.readAllLines(filePath).joinToString(separator = " ")
            val wordsList: List<String> = file.split(Regex("\\s+")).filter { it.isNotBlank() }
            wordCount = wordsList.size.toLong()
        }

        return wordCount
    }

    private fun getCharCount(): Long {
        var charCount: Long = 0
        if (Files.exists(filePath)) {
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