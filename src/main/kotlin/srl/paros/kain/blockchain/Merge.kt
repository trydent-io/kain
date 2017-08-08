package srl.paros.kain.blockchain

import org.jooby.WebSocket
import org.slf4j.LoggerFactory
import srl.paros.kain.demandFull

interface Merge {
  fun with(o: Blockchain, r: Blockchain): Blockchain
}

private val log = LoggerFactory.getLogger(BlockchainMerge::class.java)

class BlockchainMerge(ws: WebSocket) : Merge {
  private val ws = ws

  override fun with(o: Blockchain, r: Blockchain): Blockchain = when {
    (r.last.id > o.last.id && r.last.prev == o.last.hash) -> o.push(r.last).apply { ws.broadcast(o) }

    (r.last.id > o.last.id && r.size != 1 && r.last.prev != o.last.hash)  -> GENESIS_CHAIN

    (r.last.id > o.last.id && r.size == 1) -> o.apply { ws.broadcast(demandFull()) }

    else -> o.apply { log.warn("Nothing to do") }
  }
}
