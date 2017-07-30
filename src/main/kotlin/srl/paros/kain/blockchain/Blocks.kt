package srl.paros.kain.blockchain

interface ChainedBlocks : Iterable<Block> {
  val last: Block
  val size: Int get() = this.count()

  fun has(id: Id): Boolean
}

interface StackedBlocks : ChainedBlocks {
  fun mine(message: String): StackedBlocks = this.push(block(link = last.hash.value, message = message))
  fun push(block: Block): StackedBlocks
  fun pop(id: Id): Block
  fun refill(vararg blocks: Block): StackedBlocks
}

fun InMemoryBlockchain(): StackedBlocks = InMemoryBlocks(arrayOf(GENESIS))
fun ReadonlyBlockchain(vararg blocks: Block): ChainedBlocks = ReadonlyBlocks(*blocks)
