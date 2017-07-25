package srl.paros.kain

import com.fasterxml.uuid.Generators.timeBasedGenerator
import java.time.LocalDateTime
import java.time.LocalDateTime.now
import java.util.Base64.getEncoder

private val UUID = timeBasedGenerator()

private fun uuid(): String = UUID.generate().toString()
private fun LocalDateTime.asBytes() = this.toString().toByteArray()

private val GENESIS_ID = "genesis"

interface Block {
  fun hash(): ByteArray
}

class NaiveBlock(
  private val id: String = uuid(),
  private val previous: ByteArray,
  private val time: LocalDateTime = now(),
  private val data: ByteArray
) : Block {
  private val bytes = lazy {
    byteArrayOf(
      *id.toByteArray(),
      *previous,
      *time.asBytes(),
      *data
    )
  }

  override fun hash(): ByteArray = getEncoder().encode(bytes.value)

  override fun toString() = "Data $data received at $time hashed in ${hash()}"
}

val GENESIS = NaiveBlock(
  id = GENESIS_ID,
  previous = GENESIS_ID.toByteArray(),
  data = "Suppose you throw a coin enough times... suppose one day, it lands on its edge".toByteArray()
)
