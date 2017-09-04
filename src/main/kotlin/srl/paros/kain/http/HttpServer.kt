package srl.paros.kain.http

import io.undertow.Handlers.routing
import io.undertow.Undertow
import io.undertow.Undertow.builder
import io.undertow.util.HeaderMap
import io.undertow.util.Headers.CONTENT_TYPE
import io.undertow.util.PathTemplateMatch.ATTACHMENT_KEY
import org.slf4j.LoggerFactory
import srl.paros.kain.http.HttpMethod.Get
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

class HttpEndpointImpl(
  private val method: HttpMethod = Get,
  private val route: String = "",
  private val exs: Interchanges,
  private val es: HttpEndpoints
) : HttpEndpoint {
  override fun get(route: String, ex: Interchange): HttpEndpoint =
    if (route.isEmpty() || route == this.route)
      HttpEndpointImpl(Get, this.route, this.exs.add(ex), this.es)
    else
      HttpEndpointImpl(Get, route, this.exs.add(ex), HttpEndpoints())

  override fun post(route: String, exchange: Interchange): HttpEndpoint {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}
