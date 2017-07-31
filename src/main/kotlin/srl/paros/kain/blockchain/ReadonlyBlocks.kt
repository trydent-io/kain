package srl.paros.kain.blockchain

internal class ReadonlyBlocks(vararg blocks: Block): Blocks {
  private val blocks: Array<Block> = arrayOf(*blocks)

  override val last get() = blocks.last()

  override fun has(id: Id): Boolean = blocks.any { it.id == id }

  override fun iterator() = blocks.iterator()
}
