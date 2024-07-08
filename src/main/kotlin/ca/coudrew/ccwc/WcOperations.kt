package ca.coudrew.ccwc

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.attribute.BasicFileAttributes

class WcOperations(private val args: CcwcArgs) {
    private val fileAttributes: BasicFileAttributes
    private val filePath: Path = Paths.get(args.source)
    private var response: String = ""
    init {
        fileAttributes = Files.readAttributes(filePath, BasicFileAttributes::class.java)
    }

    private fun getBytes(): Long {
        return fileAttributes.size()
    }

    private fun appendToResponse(func: () -> Long): String {
        return if (this.response.isNotEmpty()) "${this.response} ${func()}" else "${func()}"
    }

    fun buildOutput(): String {
        if (args.count) {
            this.response = appendToResponse(this::getBytes)
        }
        return this.response
    }
}