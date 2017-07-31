package srl.paros.kain.blockchain

import com.fasterxml.uuid.Generators.timeBasedGenerator
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.time.LocalDateTime.ofEpochSecond
import java.time.ZoneOffset.UTC
import java.util.*

private val UUID = timeBasedGenerator()
private val ENCODER: Base64.Encoder = Base64.getEncoder()

private fun encode(bytes: ByteArray) = ENCODER.encodeToString(bytes)

private fun uuid() = UUID.generate().toString()
private fun LocalDateTime.asMills() = this.toInstant(UTC).toEpochMilli()

private val KAIN_QUOTE = "Suppose you throw a coin enough times... suppose one day, it lands on its edge"

data class Id(val value: String) {
  operator fun compareTo(id: Id): Int = when {
    value < id.value -> -1
    value == id.value -> 0
    else -> 1
  }
}

data class Hash(val value: String)
data class Statement(val value: String)

interface Block {
  val id: Id
  val time: LocalDateTime
  val statement: Statement
  val prev: Hash
  val hash: Hash
}

private class Base64Block(uuid: String, mills: Long, link: String, message: String) : Block {
  private val uuid = uuid
  private val mills = mills
  private val link = link
  private val message = message

  override val id get() = Id(uuid)

  override val time: LocalDateTime get() = ofEpochSecond(mills, 0, UTC)

  override val statement get() = Statement(message)

  override val prev get() = Hash(link)

  override val hash get() = Hash(encode(
    byteArrayOf(
      *uuid.toByteArray(),
      *mills.toString().toByteArray(),
      *link.toByteArray(),
      *message.toByteArray()
    )
  ))
}

val GENESIS: Block = Block("0", KAIN_QUOTE)
fun Block(link: String, message: String): Block = Base64Block(
  uuid = uuid(),
  mills = now().asMills(),
  link = link,
  message = message
)
