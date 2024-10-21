package toys.timberix.wootheus.http

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.micrometer.core.instrument.Clock
import io.micrometer.prometheusmetrics.PrometheusConfig
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry
import io.prometheus.metrics.model.registry.PrometheusRegistry
import kotlinx.serialization.json.Json
import toys.timberix.wootheus.config.ConfigManager
import toys.timberix.wootheus.dto.WooWebhook
import toys.timberix.wootheus.logger
import toys.timberix.wootheus.prometheus.Counters

object HttpServer {
    fun launch() {
        val prometheusRegistry = PrometheusMeterRegistry(PrometheusConfig.DEFAULT, PrometheusRegistry.defaultRegistry, Clock.SYSTEM)
        embeddedServer(Netty, port = ConfigManager.port, host = "0.0.0.0") {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            routing {
                // WooCommerce order webhook
                post("/woocommerce-webhook") {
                    runCatching {
                        val webhook = call.receive<WooWebhook>()

                        if (webhook.action.startsWith("woocommerce_order_status")) {
                            val status = webhook.action.substringAfterLast('_')

                            logger.info("Received webhook with status $status")
                            Counters.orders.labelValues(status).inc()
                        }
                        else logger.warning("Unknown WooCommerce webhook action: ${webhook.action}")

                        call.respond(HttpStatusCode.OK)
                    }.onFailure(Throwable::printStackTrace)
                }
                routing {
                    get("/metrics") {
                        call.respond(prometheusRegistry.scrape())
                    }
                }
            }
        }.start(wait = true)
    }
}
