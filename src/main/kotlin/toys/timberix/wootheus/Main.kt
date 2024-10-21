package toys.timberix.wootheus

import toys.timberix.wootheus.config.ConfigManager
import toys.timberix.wootheus.http.HttpServer
import toys.timberix.wootheus.prometheus.Counters

fun main() {
    logger.info("Initializing...")

    Counters

    logger.info("Starting http server on :${ConfigManager.port}")
    HttpServer.launch()
}
