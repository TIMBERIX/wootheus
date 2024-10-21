package toys.timberix.wootheus.dto

import kotlinx.serialization.Serializable

@Serializable
data class WooWebhook(
    val action: String
)
