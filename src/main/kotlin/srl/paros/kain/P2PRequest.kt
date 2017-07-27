package srl.paros.kain

import org.jooby.WebSocket
import java.util.function.Function
import java.util.function.Supplier

interface BlockchainRequest<T> : Function<T, Blockchain>

interface P2PBlockchainRequest : BlockchainRequest<WebSocket>

private class DefaultP2PRequest(private val blockchain: Blockchain) : P2PBlockchainRequest {
  override fun apply(ws: WebSocket): Blockchain = {
    ws.send(blockchain)
  }
}
