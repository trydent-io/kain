package srl.paros.kain.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import srl.paros.kain.api.Name
import java.sql.Driver
import java.util.Properties
import javax.sql.DataSource

interface Db {
  fun open(url: String, usr: String = "", pwd: String = ""): DataSource
}

private const val JDBC_URL = "db%s.url"
private const val USERNAME = "db%s.username"
private const val PASSWORD = "db%s.password"
private const val DRIVER = "db%s.driver"

private fun Properties.url(n: Name) = this[JDBC_URL.format(n.property)].toString()
private fun Properties.username(n: Name) = this[USERNAME.format(n.property)].toString()
private fun Properties.password(n: Name) = this[PASSWORD.format(n.property)].toString()
private fun Properties.driver(n: Name) = this[DRIVER.format(n.property)].toString()

class RawDb(urlPattern: String, driver: String) : Db {
  private val urlPattern = urlPattern
  private val driver = driver

  override fun open(url: String, usr: String, pwd: String): DataSource = HikariConfig().apply {
    jdbcUrl = urlPattern.format(url)
    driverClassName = driver
    username = usr
    password = pwd
  }.let(::HikariDataSource)
}

enum class CachedDb(val db: Db) {
  H2_FILE(h2InFile())
}

fun <T> db(urlPattern: String, driver: Class<T>): Db where T : Driver = RawDb(urlPattern, driver.name)
fun h2InFile(): Db = db("jdbc:h2:file:%s", org.h2.Driver::class.java)
