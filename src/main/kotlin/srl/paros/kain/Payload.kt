package srl.paros.kain

import com.jayway.jsonpath.DocumentContext
import com.jayway.jsonpath.JsonPath.parse
import org.jooby.Mutant
import org.jooby.WebSocket
import srl.paros.kain.blockchain.Blockchain


interface Demand {
  enum class Type { FULL, LAST }

  val type: Type
  val
}

private const val jpath = "$['demand']"

internal class FullDemand : Demand
internal class LastDemand : Demand

interface Payload {
  val demand: Demand
}

internal class PayloadWithDemand(private val message: Mutant) : Payload {
  override val demand: Demand get() = parse(message.value())
    .read(jpath, List::class.java)
    .takeIf { it.isNotEmpty() }
    .let { it?.get(0).toString() }
    .let { Demand.Type.valueOf(it) }
    .let {
      when (it) {
        Demand.Type.FULL -> FullDemand()
        Demand.Type.LAST -> LastDemand()
      }
    }
}

internal class LastPayload(message: Mutant) : Payload {
  private val message = message

  override val demand: Demand get() = parse(message.value())
    .read(jpath, List::class.java)
    .takeIf { it.isNotEmpty() }
    .let { it?.get(0).toString() }
    .let { Demand.Type.valueOf(it) }
    .takeIf { it == Demand.Type.LAST }
    .let { LastDemand() }
}

internal class BlockchainDemand(bc: Blockchain) : Demand {

}

internal class JsonDemand(message: String) : Demand {
  private val json: DocumentContext = parse(message)

  override fun yield(ws: WebSocket): Boolean {
    return json.demand
      .takeIf { it.isNotEmpty() }
      .let { it?.get(0).toString() }
      .let { Demand.Type.valueOf(it) }
      .let {
        when (it) {
          Demand.Type.Full -> ws.send("Blockchain")
          Demand.Type.Last -> ws.send("Last")
        }
      }
      .let { true }
  }
}
