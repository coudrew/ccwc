package ca.coudrew.ccwc

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.mainBody

class CcwcArgsParser(parser: ArgParser) {
    val count by parser.flagging("-c", help = "count number of bytes in file")
    val lines by parser.flagging("-l", help = "count number of lines")
    val words by parser.flagging("-w", help = "count number of words")
    val characters by parser.flagging("-m", help = "count number of characters")
    val source by parser.positional("SOURCE", help = "source filename")
}

data class CcwcArgs(
    val count: Boolean = false,
    val lines: Boolean = false,
    val words: Boolean = false,
    val characters: Boolean = false,
    val source: String
)

fun performWcOperations(ccwcArgs: CcwcArgs) {
    val wcOperations = WcOperations(ccwcArgs)
    val output = wcOperations.buildOutput()
    println(output)
}

fun main(args: Array<String>) = mainBody {
    val parsedArgs = ArgParser(args).parseInto(::CcwcArgsParser)

    parsedArgs.run {
        var ccwcArgs: CcwcArgs
        if (count.not() && lines.not() && words.not() && characters.not()) {
            ccwcArgs = CcwcArgs(true, true, true, false, source)
        } else {
            ccwcArgs = CcwcArgs(count, lines, words, characters, source)
        }
        performWcOperations(ccwcArgs)
    }
}