package srl.paros.kain

import com.github.davidmoten.rx.jdbc.Database
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import javax.sql.DataSource

internal class DbConfigImpl(
  private val url: String,
  private val username: String,
  private val password: String,
  private val driver: String) : DbConfig {

  override fun hikari() = HikariConfig().apply {
    jdbcUrl = this@DbConfigImpl.url
    username = this@DbConfigImpl.username
    password = this@DbConfigImpl.password
    driverClassName = this@DbConfigImpl.driver
  }

  override fun datasource(): DbSource = DbSourceImpl(this)
}

fun dbConfig(url: String, username: String, password: String, driver: String): DbConfig = DbConfigImpl(url, username, password, driver)

fun cachedDbConfig(url: String, username: String, password: String, driver: String): DbConfig = object : DbConfig {
  private val config = lazy { dbConfig(url, username, password, driver) }

  override fun hikari() = config.value.hikari()

  override fun datasource() = config.value.datasource()
}

interface DbSource {
  fun raw(): DataSource
}

interface RxDbSource : DbSource {
  fun database(): Database
  fun databaseAsync(): Database
}

internal class RxDbSourceImpl(private val s: DbSource) : RxDbSource {
  override fun raw(): DataSource = s.raw()

  override fun database(): Database = Database.fromDataSource(raw())

  override fun databaseAsync(): Database = database().asynchronous()
}

internal class DbSourceImpl(private val config: DbConfig) : DbSource {
  override fun raw(): DataSource = HikariDataSource(config.hikari())
}
