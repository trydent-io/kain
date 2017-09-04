package srl.paros.kain

/*

interface Yield {
  enum class Type { Merge }

  val blockchain: Blockchain

  fun gives(o: Type): Boolean
  fun with(ws: WebSocket): Unit = ws.send(this)
  fun sendWith(res: Response): Unit = res.send(this)
}

private class PeerYield(type: Yield.Type, blocks: Array<Block>) : Yield {
  private val operation = type
  private val blocks = blocks

  override val blockchain get() = Blockchain(*blocks)

  override fun gives(o: Yield.Type) = operation == o
}

fun yieldFull(b: Blockchain): Yield = PeerYield(Yield.Type.Merge, b.toList().toTypedArray())
fun yieldLast(b: Blockchain): Yield = PeerYield(Yield.Type.Merge, arrayOf(b.last))

*/
