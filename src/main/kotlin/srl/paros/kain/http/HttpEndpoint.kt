package srl.paros.kain.http

interface Request
interface Response

enum class HttpMethod { Get, Post, Put, Delete, Options, Head, Patch }

interface HttpEndpoint {
  fun get(route: String = "", exchange: Interchange): HttpEndpoint
  fun post(route: String = "", exchange: Interchange): HttpEndpoint
}

interface HttpEndpoints : Iterable<HttpEndpoint> {
  fun add(e: HttpEndpoint): HttpEndpoints
}

internal class RawHttpEndpoints : HttpEndpoints {
  override fun add(e: HttpEndpoint): HttpEndpoints {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun iterator(): Iterator<HttpEndpoint> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}

fun HttpEndpoints(es: Array<HttpEndpoint> = arrayOf()): HttpEndpoints = RawHttpEndpoints()
