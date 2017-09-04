package srl.paros.kain.http

typealias Interchange = (req: Request) -> Response

interface Interchanges : Iterable<Interchange> {
  fun add(x: Interchange): Interchanges
}

internal class RawInterchanges(private val xs: Array<Interchange>) : Interchanges {
  override fun add(x: Interchange): Interchanges = Exchanges(*xs, x)

  override fun iterator(): Iterator<Interchange> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}

fun Exchanges(vararg xs: Interchange): Interchanges = RawInterchanges(arrayOf(*xs))
