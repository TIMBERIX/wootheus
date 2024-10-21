package toys.timberix.wootheus.prometheus

import io.prometheus.metrics.core.metrics.Counter

object Counters {
    private fun counter(name: String, description: String, vararg labelNames: String) =
        Counter.builder().name("wootheus_$name").help(description).labelNames(*labelNames).register()

    /**
     * Is counted up for all status changes, so if e.g. you want to count the refunded orders, you read using the
     * label status="refunded". Or if you want to count all paid orders, you read using the label status="processing".
     */
    val orders: Counter = counter(
        "orders",
        "Counter for any orders that are placed",
        "status"
    )
}