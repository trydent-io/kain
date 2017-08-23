package srl.paros.kain

import srl.paros.kain.db.DbSource
import srl.paros.kain.db.Transform
import java.util.*

private const val SETTINGS = "application.properties"

private fun applicationProperties() = Settings::class.java.getResourceAsStream("application.properties")

data class Name(private val name: String) {
  val property get() = name.takeIf { it.isNotBlank() }?.let { ".$it" } ?: ""
}

typealias Setting = String

typealias AsDbSource<T> = (s: Settings) -> T

interface Settings : Iterable<Setting> {
  operator fun get(key: String): Setting?
  fun <S> convertIn(t: AsDbSource<S>): S where S : Settings
}

internal class SettingsImpl(private val p: Properties) : Settings {
  override fun <S : Settings> convertIn(t: AsDbSource<S>): S = t(this)

  override fun get(key: String): Setting? = p[key].toString()

  override fun iterator(): Iterator<Setting> = p.propertyNames().toList()
    .map { p[it] }
    .map { it.toString() }
    .iterator()
}

fun settings(): Settings = SettingsImpl(Properties().apply { load(applicationProperties()) })
fun settingsWith(properties: Properties): Settings = SettingsImpl(properties)
