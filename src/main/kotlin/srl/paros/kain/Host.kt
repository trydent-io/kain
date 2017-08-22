package srl.paros.kain

interface Host { val value: String }

private const val LOCALHOST = "localhost"
private const val ANYHOST = "0.0.0.0"

private const val IP4_REGEX =
  "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
  "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
  "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
  "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$"

internal data class HostImpl(override val value: String) : Host

fun ip4Host(host: String): Host? = host
  .takeIf { IP4_REGEX.toRegex().matches(it) }?.let { HostImpl(it) }


fun localhost(host: String): Host? = host.toLowerCase()
  .takeIf { it == LOCALHOST }?.let { HostImpl(it) }

fun anyhost(host: String): Host? = ip4Host(host)?.takeIf { it.value == ANYHOST }

fun host(host: String): Host = ip4Host(host)
  ?: localhost(host)
  ?: anyhost(host)
  ?: HostImpl(LOCALHOST)
