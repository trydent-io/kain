package srl.paros.kain.http

interface Host { val value: String }

private const val ANYHOST = "0.0.0.0"

private const val IP4_REGEX =
  "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
  "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
  "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
  "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$"

internal data class HostImpl(override val value: String) : Host

val LOCALHOST: Host = HostImpl("127.0.0.1")

fun Host(host: String): Host? = host.toLowerCase()
  .takeIf { ANYHOST == it || IP4_REGEX.toRegex().matches(it) || it == "localhost" }
  ?.let { HostImpl(it) }
