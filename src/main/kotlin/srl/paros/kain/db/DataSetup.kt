package srl.paros.kain.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import srl.paros.kain.core.Name
import srl.paros.kain.core.Setup
import srl.paros.kain.core.Setups
import srl.paros.kain.core.settings
import srl.paros.kain.core.settingsWith
import java.util.Properties
import javax.sql.DataSource

interface DataSetup : Setup<DataSource>

private const val JDBC_URL = "db%s.url"
private const val USERNAME = "db%s.username"
private const val PASSWORD = "db%s.password"
private const val DRIVER = "db%s.driver"

private fun Properties.url(name: Name) = this.getProperty(JDBC_URL.format(name.property))
private fun Properties.username(name: Name) = this.getProperty(USERNAME.format(name.property))
private fun Properties.password(name: Name) = this.getProperty(PASSWORD.format(name.property))
private fun Properties.driver(name: Name) = this.getProperty(DRIVER.format(name.property))

internal class HikariSetup(
  private val properties: Properties,
  private val name: Name
) : DataSetup {
  private fun config() = HikariConfig().apply {
    jdbcUrl = properties.url(name)
    username = properties.username(name)
    password = properties.password(name)
    driverClassName = properties.driver(name)
  }

  override fun get(name: Name): DataSource = HikariDataSource(config())

  override fun iterator(): Iterator<String> {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}


fun dbSettings(): DataSetup = settings().convertIn(::dbSettings)
fun dbSettings(setup: Setups): DataSetup = HikariSetup(setup)
fun dbSettingsWith(properties: Properties): DataSetup = settingsWith(properties).convertIn(::dbSettings)
