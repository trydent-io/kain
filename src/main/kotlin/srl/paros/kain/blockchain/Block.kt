package srl.paros.kain.blockchain

import com.fasterxml.uuid.Generators.timeBasedGenerator
import srl.paros.kain.db.RxDb
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset.UTC
import java.util.Base64
import java.util.UUID

private val UUID = timeBasedGenerator()
private val ENCODER: Base64.Encoder = Base64.getEncoder()

private fun encode(bytes: ByteArray) = ENCODER.encodeToString(bytes)

private fun uuid() = UUID.generate().toString()
private fun LocalDateTime.asMills() = this.toInstant(UTC).toEpochMilli()

private val KAIN_QUOTE = "Suppose you throw a coin enough times... suppose one day, it lands on its edge"

data class Statement internal constructor(val value: String)

fun statement(v: String): Statement? = v
  .takeIf { it.length in 1..255 }
  ?.let(::Statement)

interface Block {
  val uuid: UUID
  val time: LocalDateTime
  val statement: Statement
}

private fun Long.asLocalDateTime(): LocalDateTime = Instant
  .ofEpochMilli(this)
  .atZone(ZoneId.systemDefault())
  .toLocalDateTime()

private fun String.asUUID(): UUID = java.util.UUID.fromString(this)

internal class RawBlock(
  private val value1: String,
  private val value2: Long,
  private val value3: String
) : Block {
  override val uuid: UUID get() = value1.asUUID()
  override val time: LocalDateTime get() = value2.asLocalDateTime()
  override val statement: Statement get() = statement(value3)!!
}

internal class DbBlock(private val db: RxDb, private val id: Long) : Block {
  override val uuid: UUID
    get() =
      override
  val time: LocalDateTime
    get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
  override val statement: Statement
    get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

}

/*
internal class HashedBlock(uuid: String, mills: Long, link: String, data: String) : Block {
  private val uuid = uuid
  private val mills = mills
  private val link = link
  private val data = data

  override val id get() = uuid

  override val time: LocalDateTime get() = ofEpochSecond(mills, 0, UTC)

  override val statement get() = data

  override val prev get() = link

  override val hash get() = encode(
    byteArrayOf(
      *uuid.toByteArray(),
      *mills.toString().toByteArray(),
      *link.toByteArray(),
      *data.toByteArray()
    )
  )
}

internal class MinedBlock(prev: Hash, data: String) : Block {
  private val block: Block = HashedBlock(
    uuid = uuid(),
    mills = now().asMills(),
    link = prev,
    data = data
  )

  override val id: Id get() = block.id

  override val time: LocalDateTime get() = block.time

  override val statement: Statement get() = block.statement

  override val prev: Hash get() = block.prev

  override val hash: Hash get() = block.hash
}

val GENESIS: Block = MinedBlock("0", KAIN_QUOTE)

fun mineBlock(prev: Hash, data: String): Block = MinedBlock(prev, data)
*/
