package srl.paros.kain.blockchain

interface Blockchain : Iterable<Block> {
  val last: Block
  val size: Int get() = this.count()

  fun mine(data: String) = push(Block(last.hash, data))
  fun push(block: Block): Blockchain
}

internal class ImmutableBlockchain(vararg blocks: Block) : Blockchain {
  private val blocks: Array<Block> = arrayOf(*blocks)

  override val last: Block get() = blocks.last()

  override fun push(block: Block): Blockchain = ImmutableBlockchain(*blocks, block)

  override fun iterator(): Iterator<Block> = blocks.iterator()
}

fun Blockchain(): Blockchain = ImmutableBlockchain(GENESIS)
fun ReadonlyBlockchain(vararg blocks: Block): Blockchain = ImmutableBlockchain(*blocks)
