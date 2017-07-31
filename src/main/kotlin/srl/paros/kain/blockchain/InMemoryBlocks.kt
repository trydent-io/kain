package srl.paros.kain.blockchain

internal class InMemoryBlocks(private var blocks: Array<Block>) : MinedBlocks {

  override val last get() = blocks.last()

  override fun push(block: Block): StackedBlocks {
    synchronized(blocks) {
      blocks = arrayOf(*blocks, block)
    }
    return this
  }

  override fun pop(id: Id): Block = blocks.filter { it.id == id }.first()

  override fun has(id: Id) = blocks.any { it.id == id }

  override fun iterator() = blocks.sortedBy { it.hash.value }.iterator()

  override fun refill(vararg blocks: Block): StackedBlocks {
    synchronized(this.blocks) {
      this.blocks = arrayOf(*blocks)
    }

    return this
  }

}
