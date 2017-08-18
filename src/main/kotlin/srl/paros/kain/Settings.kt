package srl.paros.kain

import com.github.davidmoten.rx.jdbc.Database
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import spark.servlet.SparkApplication
import java.util.*
import javax.sql.DataSource

private const val SETTINGS = "application.properties"

interface Settings {
  fun server(): SparkApplication
  fun cluster(): Cluster
  fun rdbms(): Rdbms
}

interface Cluster {

}

private fun Properties.url(): String = this.getProperty("db.url")
private fun Properties.username(): String = this.getProperty("db.username")
private fun Properties.password(): String = this.getProperty("db.password")

internal class SettingsImpl(p: Properties) : Settings {
  override fun server(): SparkApplication = App()

  override fun cluster(): Cluster {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  private val p = p

  init {
    p.load(this.javaClass.getResourceAsStream(SETTINGS))
  }

  override fun rdbms(): Rdbms = RdbmsImpl(p.url(), p.username(), p.password())
}

interface Rdbms {
  fun config(): HikariConfig
  fun datasource(): DataSource = HikariDataSource(config())
  fun database(): Database = Database.fromDataSource(datasource())
}

internal class RdbmsImpl(url: String, username: String, password: String) : Rdbms {
  private val url = url
  private val username = username
  private val password = password

  override fun config(): HikariConfig = HikariConfig().apply {
    jdbcUrl = url
    username = username
    password = password
  }
}
