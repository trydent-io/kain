package srl.paros.kain.http

import io.undertow.Handlers
import io.undertow.Undertow
import io.undertow.util.Headers
import io.undertow.util.PathTemplateMatch

interface Route {
  val value: String
}

interface Request
interface Response

interface Endpoint {
  val route: Route
  fun exchange(req: Request): Response
  fun exchange(req: Request, res: Response): Response
}

class RestEndpoint

val undertow = Undertow.builder()
  .addHttpListener(8080, "127.0.0.1")
  .setHandler { _ ->
    Handlers.pathTemplate()
      .add("/item/{itemId}", {
        with(it) {
          responseHeaders.put(Headers.CONTENT_TYPE, "application/json")
          getAttachment(PathTemplateMatch.ATTACHMENT_KEY).parameters["itemId"]
          queryParameters["itemId"]?.first
        }
      })
  }
  .build()
