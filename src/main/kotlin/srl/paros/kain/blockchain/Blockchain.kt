package srl.paros.kain.blockchain

import srl.paros.kain.db.DbSession
import java.util.UUID

interface Blocks : Iterable<Block> {
  fun push(block: Block): Blocks
  fun pop(uuid: UUID)
}

internal class DbBlocks(private val db: DbSession) : Blocks {
  override fun pop(uuid: UUID) = db.jdbc()
    .sql("delete from blocks where uuid = ?")
    .set(uuid.toString())
    .execute()
    .commit()

  override fun push(block: Block): Blocks {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun iterator(): Iterator<Block> = db.jdbc()
    .sql("select uuid, timestamp, statement from blocks order by timestamp")
    .select { rs, _ ->
      val bs = mutableListOf<Block>()
      while (rs.next()) bs.add(RawBlock(rs.getString(0), rs.getLong(1), rs.getString(2)))
      bs.iterator()
    }
}
