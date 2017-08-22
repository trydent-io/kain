package srl.paros.kain.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import srl.paros.kain.DbSource
import javax.sql.DataSource

internal class HikariDbSourceImpl(private val config: HikariConfig) : DbSource {
  override fun dataSource(): DataSource = HikariDataSource(config)
}

fun HikariDbSource(config: HikariConfig): DbSource = HikariDbSourceImpl(config)
