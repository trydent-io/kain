package srl.paros.kain

import com.github.davidmoten.rx.jdbc.Database
import com.zaxxer.hikari.HikariDataSource
import javax.sql.DataSource


interface DbSource {
  fun dataSource(): DataSource
}

interface RxDbSource : DbSource {
  fun database(): Database
  fun databaseAsync(): Database
}


internal class DbConfigImpl(
  private val url: String,
  private val username: String,
  private val password: String,
  private val driver: String) : DbConfig {

  override fun datasource(): DbSource = DbSourceImpl(this)
}

internal class DbSourceImpl(private val config: DbConfig) : DbSource {
  override fun dataSource(): DataSource = HikariDataSource(config.hikari())
}

fun dbConfig(url: String, username: String, password: String, driver: String): DbConfig = DbConfigImpl(url, username, password, driver)

fun cachedDbConfig(url: String, username: String, password: String, driver: String): DbConfig = object : DbConfig {
  private val config = lazy { dbConfig(url, username, password, driver) }

  override fun hikari() = config.value.hikari()

  override fun datasource() = config.value.datasource()
}


internal class RxDbSourceImpl(private val s: DbSource) : RxDbSource {
  override fun dataSource(): DataSource = s.dataSource()

  override fun database(): Database = Database.fromDataSource(dataSource())

  override fun databaseAsync(): Database = database().asynchronous()
}

