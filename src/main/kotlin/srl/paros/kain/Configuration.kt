package srl.paros.kain

import com.zaxxer.hikari.HikariConfig
import java.util.*

private const val SETTINGS = "application.properties"

private fun applicationProperties() = Settings::class.java.getResourceAsStream("application.properties")

fun named(v: String) = (v.takeIf { it.isNotBlank() }?.let { ".$it" } ?: "")

interface Settings : Iterable<String> {
  fun xml(): String
  fun json(): String
}

internal class PSettings(p: Properties) : Settings {
  private val p = p

  override fun xml(): String = "<xml>$p</xml>"

  override fun json(): String = """{ json: "$p" }"""

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
  load(applicationProperties())
}.let { PSettings(it) }

fun settingsFrom(p: Properties): Settings = PSettings(p)

interface DbConfig {
  fun datasource(): DbSource
}

interface HikariDbConfig {
  fun hikari(): HikariConfig
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
