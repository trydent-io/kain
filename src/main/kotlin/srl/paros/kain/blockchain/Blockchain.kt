package srl.paros.kain.blockchain

import com.github.davidmoten.rx.jdbc.Database
import rx.Observable
import java.time.LocalDateTime
import java.time.ZoneOffset.UTC
import java.util.UUID
import javax.sql.DataSource

interface Blocks : Iterable<Block> {
  fun push(uuid: UUID, locktime: LocalDateTime, input: Statement, output: Statement): Int
  fun pop(uuid: UUID): Boolean
  fun observe(): Observable<Block>
}

class DbBlocks(private val source: DataSource) : Blocks {
  private val db: Database = Database.fromDataSource(source)

  override fun pop(uuid: UUID): Boolean = db
    .select("delete from blocks where uuid = :uuid")
    .parameter("uuid", uuid.toString())
    .count()
    .exists { it == 1 }
    .toBlocking()
    .first()

  override fun push(uuid: UUID, locktime: LocalDateTime, input: Statement, output: Statement): Int = db
    .update("insert into blocks(uuid, locktime, input, output) values(:uuid, :locktime, :input, :output)")
    .parameter("uuid", uuid.toString())
    .parameter("locktime", locktime.toEpochSecond(UTC))
    .parameter("input", input.value)
    .parameter("output", output.value)
    .returnGeneratedKeys()
    .getAs(Int::class.java)
    .toBlocking()
    .first()

  override fun observe(): Observable<Block> = db
    .select("select uuid, locktime, input, output from blocks order by uuid")
    .autoMap(RawBlock::class.java)
    .cast(Block::class.java)

  override fun iterator(): Iterator<Block> = observe() .toBlocking().iterator
}
