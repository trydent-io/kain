package srl.paros.kain.db

import srl.paros.kain.DbSource
import srl.paros.kain.Settings
import srl.paros.kain.named
import java.util.*

interface DbSettings : Settings {
  fun dbSource(named: String = ""): DbSource
}

internal class PDbSettings(private val properties: Properties) : DbSettings {
  override fun dbSource(name: String): DbSource {
    val n = named(name)
    return HikariDbSource(HikariDbSettingsWith(properties))
  }

  override fun xml(): String {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun json(): String {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun iterator(): Iterator<String> = properties
    .propertyNames().toList()
    .map { properties[it] }
    .map { it.toString() }
    .iterator()
}

fun DbSettingsWith(properties: Properties): DbSettings = PDbSettings(properties)
