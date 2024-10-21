package toys.timberix.wootheus.config

object ConfigManager {
    private fun configValue(key: String) = System.getenv("WOOTHEUS_${key.uppercase()}")

    val port = configValue("port")?.toInt() ?: 8081
}