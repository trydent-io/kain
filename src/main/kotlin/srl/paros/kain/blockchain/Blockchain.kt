package srl.paros.kain.blockchain

interface Blockchain : Iterable<Block> {
  val last: Block
  val size: Int get() = this.count()

  fun mine(data: String) = push(block(last.hash, data))
  fun push(block: Block): Blockchain
}

class ReadonlyBlockchain(vararg blocks: Block) : Blockchain {
  private val blocks: Array<Block> = arrayOf(*blocks)

  override val last: Block get() = blocks.last()

  override fun push(block: Block): Blockchain = ReadonlyBlockchain(*blocks, block)

  override fun iterator(): Iterator<Block> = blocks.iterator()
}

val GENESIS_CHAIN = ReadonlyBlockchain(GENESIS)
