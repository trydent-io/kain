package srl.paros.kain

import com.fasterxml.uuid.Generators.timeBasedGenerator
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.time.ZoneOffset.UTC
import java.util.*

private val UUID = timeBasedGenerator()
private val ENCODER:Base64.Encoder = Base64.getEncoder()

private fun uuid(): String = UUID.generate().toString()
private fun LocalDateTime.asBytes() = this.toEpochSecond(UTC).toString().toByteArray()

private val GENESIS_ID = "ABEL"
private val GENESIS_HASH = String(Base64.getEncoder().encode(GENESIS_ID.toByteArray()))
private val GENESIS_DATA = "Suppose you throw a coin enough times... suppose one day, it lands on its edge"

interface Block {
  fun hash(): String
}

private class Base64Block(
  private val id: String = uuid(),
  private val previous: String,
  private val time: LocalDateTime = now(),
  private val data: String
) : Block {
  private val bytes = byteArrayOf(
    *id.toByteArray(),
    *previous.toByteArray(),
    *time.asBytes(),
    *data.toByteArray()
  )

  override fun hash(): String = ENCODER.encodeToString(bytes)

  override fun toString() = "Data $data received at $time hashed in ${hash()}"
}

fun block(previous: String, data: String): Block = Base64Block(previous = previous, data = data)
fun identifiedBlock(id: UUID, previous: String, data: String): Block = Base64Block(
  id = id.toString(),
  previous = previous,
  data = data
)


val GENESIS: Block = Base64Block(
  id = GENESIS_ID,
  previous = GENESIS_HASH,
  data = GENESIS_DATA
)
