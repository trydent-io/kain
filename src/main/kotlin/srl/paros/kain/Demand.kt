package srl.paros.kain

import com.jayway.jsonpath.JsonPath
import org.jooby.Response
import org.jooby.WebSocket
import srl.paros.kain.Demand.Type.Full
import srl.paros.kain.Demand.Type.Last
import srl.paros.kain.blockchain.Block
import srl.paros.kain.blockchain.Blockchain
import srl.paros.kain.blockchain.ReadonlyBlockchain

interface Demand {
  enum class Type(val value:String) { Full("full"), Last("last") }

  fun yield(ws: WebSocket): Boolean
}

class JsonDemand(private val m: String) : Demand {
  override fun yield(ws: WebSocket) = true
}

