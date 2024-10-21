package toys.timberix.wootheus

import java.io.File
import java.util.logging.FileHandler
import java.util.logging.Formatter
import java.util.logging.LogRecord
import java.util.logging.Logger

private const val logFile = "wootheus.log"

val logger: Logger = Logger.getLogger("toys.timberix.wootheus").apply {
    File(logFile).writeText("")
    addHandler(FileHandler(logFile).apply {
        formatter = object : Formatter() {
            override fun format(record: LogRecord?): String {
                return "${record?.level}: ${record?.message}\n"
            }
        }
    })
}