package srl.paros.kain.server

import srl.paros.kain.HttpPort
import srl.paros.kain.Name
import srl.paros.kain.Setting
import srl.paros.kain.Settings
import srl.paros.kain.host
import srl.paros.kain.http.HttpServer
import srl.paros.kain.http.httpServer
import srl.paros.kain.settings

interface HttpServerSettings : Settings {
  fun httpServer(name: Name = Name("")): HttpServer
}

private const val HS = "httpServer"

internal class HttpServerSettingsImpl(private val settings: Settings) : HttpServerSettings {
  override fun httpServer(name: Name): HttpServer = name.property
    .let {
      httpServer(
        host(this["$HS$it.host"].toString()),
        HttpPort(this["$HS$it.port"].toString().toInt())
      )
    }

  override fun get(key: String): Setting? = key.takeIf { it.startsWith("httpServer") }?.let { settings[it] }

  override fun iterator(): Iterator<Setting> = settings.filter { it.startsWith("httpServer") }.iterator()
}

fun httpServerSettings(): HttpServerSettings = settings().let(::httpServerSettings)
fun httpServerSettings(settings: Settings): HttpServerSettings = HttpServerSettingsImpl(settings)
