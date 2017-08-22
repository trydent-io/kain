package srl.paros.kain

import com.zaxxer.hikari.HikariConfig
import java.util.*

private const val SETTINGS = "application.properties"

interface Settings : Iterable<String> {
  fun xml(): String
  fun json(): String

  fun serverConfig(server: String = ""): ServerConfiguration
  fun clusterConfig(cluster: String = ""): ClusterConfiguration
  fun dbConfig(database: String = ""): DbConfig
}

internal class PSettings(p: Properties) : Settings {
  private val p = p

  override fun xml(): String = "<xml>$p</xml>"

  override fun json(): String = """{ json: "$p" }"""

  private fun named(v: String): String = (v.takeIf { it.isNotBlank() }?.let { ".$it" } ?: "")

  override fun serverConfig(server: String): ServerConfiguration {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun clusterConfig(cluster: String): ClusterConfiguration {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun dbConfig(database: String): DbConfig = named(database).let {
    dbConfig(
      p.getProperty("db$it.url"),
      p.getProperty("db$it.username"),
      p.getProperty("db$it.password"),
      p.getProperty("db$it.driver"))
  }

  override fun iterator(): Iterator<String> = p.propertyNames()
    .toList()
    .map { "property[$it] = ${p[it]}" }
    .iterator()
}

fun defaultSettings(): Settings = Properties().apply {
  load(Settings::class.java.getResourceAsStream("application.properties"))
}.let { PSettings(it) }

fun settingsFrom(p: Properties): Settings = PSettings(p)

interface DbConfig {
  fun hikari(): HikariConfig
  fun datasource(): DbSource
}

interface ServerConfiguration {
  fun httpServer(): HttpServer
  fun httpPort(): HttpPort
  fun httpsPort(): HttpsPort
}

interface ClusterConfiguration {
  fun cluster(): Cluster
  fun clusterPort(): ClusterPort
}
