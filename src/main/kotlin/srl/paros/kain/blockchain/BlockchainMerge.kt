package srl.paros.kain.blockchain

import srl.paros.kain.DemandFull

typealias Merge = (o: Blockchain, r: Blockchain) -> Blockchain

val mergeLast: Merge = { o: Blockchain, r: Blockchain -> when {
  (r.last.id > o.last.id && r.last.prev == o.last.hash) -> o.push(r.last)
}

val mergeFull: Merge = { o: Blockchain, r: Blockchain -> when {
  (r.last.id > o.last.id && r.size != 1 && r.last.prev != o.last.hash)  -> Blockchain()
  }
}

val tryEnquiry = { o: Blockchain, r: Blockchain ->
  when {
    (r.last.id > o.last.id && r.size == 1) -> DemandFull()
    else -> throw IllegalStateException()
  }
}
