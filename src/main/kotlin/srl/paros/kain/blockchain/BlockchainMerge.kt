package srl.paros.kain.blockchain

import srl.paros.kain.DemandFull

val tryLatest = { o: StackedBlocks, r: Blocks ->
  if (r.last.id > o.last.id && r.last.prev == o.last.hash) {
    o.push(r.last)
  }
}

val tryRefill = { o: StackedBlocks, r: Blocks ->
  if (r.last.id > o.last.id && r.size != 1 && r.last.prev != o.last.hash) {
    o.refill(*r.toList().toTypedArray())
  }
}

val tryEnquiry = { o: StackedBlocks, r: Blocks ->
  when {
    (r.last.id > o.last.id && r.size == 1) -> DemandFull()
    else -> throw IllegalStateException()
  }
}
