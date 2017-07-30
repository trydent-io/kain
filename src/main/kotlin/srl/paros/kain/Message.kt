package srl.paros.kain

import org.jooby.Response
import org.jooby.WebSocket
import srl.paros.kain.Demand.Type.Full
import srl.paros.kain.Demand.Type.Last
import srl.paros.kain.blockchain.Block
import srl.paros.kain.blockchain.ChainedBlocks
import srl.paros.kain.blockchain.ReadonlyBlockchain

interface Demand {
  enum class Type { Last, Full }

  fun needs(o: Type): Boolean
}

interface Yield {
  enum class Type { Merge }

  val blockchain: ChainedBlocks

  fun gives(o: Type): Boolean
  fun with(ws: WebSocket): Unit = ws.send(this)
  fun sendWith(res: Response): Unit = res.send(this)
}

private class PeerDemand(type: Demand.Type, payload: Any): Demand {
  private val operation = type
  private val payload = payload

  override fun needs(o: Demand.Type) = operation == o
}

private class PeerYield(type: Yield.Type, blocks: Array<Block>): Yield {
  private val operation = type
  private val blocks = blocks

  override val blockchain get() = ReadonlyBlockchain(*blocks)

  override fun gives(o: Yield.Type) = operation == o
}

fun DemandFull(): Demand = PeerDemand(Full, "Nothing")
fun DemandLast(): Demand = PeerDemand(Last, "Nothing")

fun YieldLast(blockchain: ChainedBlocks): Yield = PeerYield(Yield.Type.Merge, arrayOf(blockchain.last))
fun YieldFull(blockchain: ChainedBlocks): Yield = PeerYield(Yield.Type.Merge, blockchain.toList().toTypedArray())
