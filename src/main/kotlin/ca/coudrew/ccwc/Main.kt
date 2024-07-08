package ca.coudrew.ccwc

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.mainBody
import com.xenomachina.argparser.SystemExitException
import com.xenomachina.argparser.default
import ca.coudrew.ccwc.Operations

class CcwcArgsParser(parser: ArgParser) {
    val count by parser.flagging("-c", help = "count number of bytes in file")
    val lines by parser.flagging("-l", help = "count number of lines")
    val words by parser.flagging("-w", help = "count number of words")
    val characters by parser.flagging("-m", help = "count number of characters")
    val source by parser.positional("SOURCE", help = "source filename")
}

data class CcwcArgs(val count: Boolean, val lines: Boolean, val words: Boolean, val characters: Boolean, val source: String)

fun main(args: Array<String>) = mainBody {
    val parsedArgs = ArgParser(args).parseInto(::CcwcArgsParser)

    parsedArgs.run {
        val ccwcArgs = CcwcArgs(count, lines, words, characters, source)
        val operations = Operations(ccwcArgs)
        println(operations.buildResponse())
    }
}