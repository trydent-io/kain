package srl.paros.kain.db

import com.zaxxer.hikari.HikariConfig
import srl.paros.kain.DbSource
import srl.paros.kain.named
import java.util.*

interface HikariDbSettings : DbSettings {
  fun config(db: String = ""): HikariConfig
}

internal class PHikariDbSettings(private val properties: Properties) : HikariDbSettings {
  override fun config(db: String): HikariConfig {
    val named = named(db)
    val config = HikariConfig()
    config.jdbcUrl = properties["db$named.url"].toString()
    config.username = properties["db$named.username"].toString()
    config.password = properties["db$named.password"].toString()
    config.driverClassName = properties["db$named.driver"].toString()

    return config
  }

  override fun dbSource(named: String): DbSource = HikariDbSource(config(named))

  override fun xml(): String {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun json(): String {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun iterator(): Iterator<String> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

}

fun HikariDbSettingsWith(properties: Properties): HikariDbSettings = PHikariDbSettings(properties)
