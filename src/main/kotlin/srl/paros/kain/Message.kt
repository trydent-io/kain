package srl.paros.kain

import srl.paros.kain.Message.Type.QueryAll
import srl.paros.kain.Message.Type.QueryChain
import srl.paros.kain.Message.Type.QueryLast

interface Message {
  enum class Type { QueryAll, QueryLast, QueryChain }

  fun isFor(type: Type): Boolean
}

private class PeerMessage(
  private val data: String,
  private val type: Message.Type
) : Message {
  override fun isFor(type: Message.Type): Boolean = this.type == type
}

fun queryAllMessage(data: String): Message = PeerMessage(data, QueryAll)
fun queryLastMessage(data: String): Message = PeerMessage(data, QueryLast)
fun queryChainMessage(data: String): Message = PeerMessage(data, QueryChain)
