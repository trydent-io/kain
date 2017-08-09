package srl.paros.kain

import com.jayway.jsonpath.DocumentContext
import com.jayway.jsonpath.JsonPath.parse
import org.jooby.WebSocket

interface Demand {
  enum class Type { Full, Last }

  fun yield(ws: WebSocket): Boolean
}

internal class JsonDemand(private val message: String) : Demand {
  private val json: DocumentContext = parse(message)

  override fun yield(ws: WebSocket): Boolean = json.read("$['demand']", List::class.java)
    .takeIf { it.isNotEmpty() }
    .let { it?.get(0).toString() }
    .let { Demand.Type.valueOf(it) }
    .let {
      when (it) {
        Demand.Type.Full -> ws.send("Blockchain").let { true }
        Demand.Type.Last -> ws.send("Last").let { true }
      }
    }
    .not()
}
