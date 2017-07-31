package srl.paros.kain.blockchain

interface Blocks : Iterable<Block> {
  val last: Block
  val size: Int get() = this.count()
}

interface StackedBlocks : Blocks {
  fun push(block: Block): StackedBlocks
  fun pop(id: Id): Block
  fun refill(vararg blocks: Block): StackedBlocks
}

typealias Callback = (bs: Array<Block>) -> Unit

interface Blockchain : Blocks {
  fun mine(data: String): Blockchain
  fun merge(b: Blockchain): Unit
  fun merge(b: Blockchain, c: Callback) {
    merge(b)
    c(this.toList().toTypedArray())
  }
}

internal class DefaultBlockchain(blocks: StackedBlocks) : Blockchain {
  private val blocks = blocks

  override val last get() = blocks.last

  override fun mine(data: String): Blockchain = this.apply {
    blocks.push(
      Block(
        link = blocks.last.hash.value,
        message = data
      )
    )
  }

  override fun merge(b: Blockchain) {
    val hold = blocks.last
    val given = b.last

    when {
      b.last.id > blocks.last.id ->
        when {
          b.last.prev == blocks.last.hash -> blocks.push(b.last).toList().toTypedArray()
          b.size == 1 -> //
          else ->
        }
      else ->
    }

    if (r.last.id > o.last.id && r.last.prev == o.last.hash) {
      o.push(r.last)
    }
  }
}

fun Blockchain(): MinedBlocks = InMemoryBlocks(arrayOf(GENESIS))
fun ReadonlyBlockchain(vararg blocks: Block): Blocks = ReadonlyBlocks(*blocks)
