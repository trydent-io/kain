package srl.paros.kain.db

import srl.paros.kain.Name
import srl.paros.kain.Setting
import srl.paros.kain.Settings
import srl.paros.kain.settings
import srl.paros.kain.settingsWith
import java.util.*

interface DbSettings : Settings {
  fun dbSource(name: Name = Name("")): DbSource
}

internal class DbSettingsImpl(private val settings: Settings) : DbSettings {
  override fun get(key: String): Setting? = settings[key]

  override fun iterator(): Iterator<Setting> = settings.iterator()

  override fun dbSource(name: Name): DbSource = name.value.let {
    dbSource(
      this["db$it.url"] ?: "localhost",
      this["db$it.username"],
      this["db$it.password"],
      this["db$it.driver"]
    )
  }

}

fun dbSettings(): DbSettings = settings().let(::dbSettings)
fun dbSettings(settings: Settings): DbSettings = DbSettingsImpl(settings)
fun dbSettingsWith(properties: Properties): DbSettings = settingsWith(properties).let(::dbSettings)
