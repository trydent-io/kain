package srl.paros.kain.http

import io.undertow.Handlers.routing
import io.undertow.Undertow
import io.undertow.Undertow.*
import io.undertow.util.HeaderMap
import io.undertow.util.Headers.CONTENT_TYPE
import io.undertow.util.PathTemplateMatch.ATTACHMENT_KEY
import org.slf4j.LoggerFactory
import java.util.function.Supplier

data class Port(val value: Int)

interface HttpServer : Supplier<Undertow>

private fun HeaderMap.contentType(type: String) = this.put(CONTENT_TYPE, type)

private val log = LoggerFactory.getLogger(DefaultHttpServer::class.java)

class DefaultHttpServer(
  private val host: Host,
  private val port: Port
) : HttpServer {
  override fun get(): Undertow = builder()
    .addHttpListener(port.value, host.value)
    .setHandler(
      routing(false)
        .get("/hello", {
          it.responseHeaders.contentType("text/plain")
          it.responseSender.send("Hello Blockchain")
        })
        .get("/item/{itemId}", {
          with(it) {
            responseHeaders.put(CONTENT_TYPE, "application/json")
            getAttachment(ATTACHMENT_KEY).parameters["itemId"]
            val id = queryParameters["itemId"]?.first
            log.info("ID: $id")
          }
        })
    )
    .build()
}
