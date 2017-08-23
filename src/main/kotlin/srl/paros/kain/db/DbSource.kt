package srl.paros.kain.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import javax.sql.DataSource

typealias Transform<T> = (s: DbSource) -> T

interface DbSource {
  fun dataSource(): DataSource
  fun <D> convertIn(t: Transform<D>): D where D : DbSource
}

internal class DbSourceImpl(
  private val url: String,
  private val usr: String,
  private val pwd: String,
  private val drv: String
) : DbSource {
  private fun hikari() = HikariConfig().apply {
    jdbcUrl = url
    username = usr
    password = pwd
    driverClassName = drv
  }

  override fun dataSource(): DataSource = HikariDataSource(hikari())

  override fun <D : DbSource> convertIn(t: Transform<D>): D = t(this)
}

fun dbSource(url: String, usr: String, pwd: String, drv: String): DbSource = DbSourceImpl(url, usr, pwd, drv)
