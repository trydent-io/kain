package srl.paros.kain

interface Blockchain : Iterable<Block> {
  fun add(data: String): Blockchain
  fun sort(): Blockchain
  fun last(): Block
}

private class InMemoryBlockchain(private var chain: Array<Block>) : Blockchain {
  override fun add(data: String): Blockchain {
    synchronized(this) {
      chain = arrayOf(*chain, block(last().hash(), data))
    }

    return this
  }

  override fun last() = chain.last()

  override fun sort(): Blockchain {
    synchronized(this) {
      chain.sortBy { it.hash() }
    }

    return this
  }

  override fun iterator() = chain.iterator()
}

fun inMemoryBlockchain(): Blockchain = InMemoryBlockchain(arrayOf(GENESIS))
