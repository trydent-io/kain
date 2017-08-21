package srl.paros.kain

import com.zaxxer.hikari.HikariConfig

private const val SETTINGS = "application.properties"

interface Settings : Iterable<String> {
  fun asXml(): String
  fun asJson(): String

  fun serverConfig(server: String = ""): ServerConfiguration
  fun clusterConfig(cluster: String = ""): ClusterConfiguration
  fun databaseConfig(database: String = ""): DbConfiguration
}

interface DbConfiguration {
  fun hikari(): HikariConfig
  fun port(): Port
  fun host(): Host
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
