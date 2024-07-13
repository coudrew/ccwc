package ca.coudrew.ccwc

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import com.xenomachina.argparser.mainBody
import java.io.InputStreamReader
import java.io.BufferedReader

class CcwcArgsParser(parser: ArgParser) {
    val count by parser.flagging("-c", help = "count number of bytes in file")
    val lines by parser.flagging("-l", help = "count number of lines")
    val words by parser.flagging("-w", help = "count number of words")
    val characters by parser.flagging("-m", help = "count number of characters")
    val source by parser.positional("SOURCE", help = "source filename").default("")
}

data class CcwcArgs(
    val count: Boolean = false,
    val lines: Boolean = false,
    val words: Boolean = false,
    val characters: Boolean = false,
    val source: String = ""
)

fun main(args: Array<String>) = mainBody {
    val parsedArgs = ArgParser(args).parseInto(::CcwcArgsParser)
    val reader = BufferedReader(InputStreamReader(System.`in`))
    val readerInput: MutableList<String> = mutableListOf()

    reader.use {
        if (reader.ready()) {
            var line = reader.readLine()
            while (line != null) {
                readerInput.add(line)
                line = reader.readLine()
            }
        }
    }

    parsedArgs.run {
        val ccwcArgs: CcwcArgs = if (count.not() && lines.not() && words.not() && characters.not()) {
            CcwcArgs(true, true, true, false, source)
        } else {
            CcwcArgs(count, lines, words, characters, source)
        }

        val wcOperations = if (readerInput.size > 0) WcOperations(ccwcArgs, readerInput) else WcOperations(ccwcArgs)
        val output = wcOperations.buildOutput()

        println(output)
    }
}