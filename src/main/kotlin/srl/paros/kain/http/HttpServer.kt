package srl.paros.kain.http

import io.undertow.Undertow
import io.undertow.util.Headers
import srl.paros.kain.Host
import srl.paros.kain.HttpPort
import java.util.concurrent.atomic.AtomicReference

interface HttpServer {
  fun start()
  fun stop()
}

internal class HttpServerImpl(
  private val host: Host,
  private val port: HttpPort
) : HttpServer {
  private val ref: AtomicReference<Undertow> = AtomicReference()

  override fun start() {
    ref.set(Undertow.builder()
      .addHttpListener(port.value, host.value)
      .setHandler {
        with(it) {
          responseHeaders.put(Headers.CONTENT_TYPE, "text/plain")
          responseSender.send("Hello World")
        }
      }
      .build())
    ref.get().start()
  }

  override fun stop() {
    ref.get()?.stop()
  }
}

fun httpServer(host: Host, httpPort: HttpPort): HttpServer = HttpServerImpl(host, httpPort)
