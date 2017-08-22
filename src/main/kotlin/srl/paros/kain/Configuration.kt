package srl.paros.kain

import java.util.*

private const val SETTINGS = "application.properties"

private fun applicationProperties() = Settings::class.java.getResourceAsStream("application.properties")

data class Name(private val name: String) {
  val value get() = name.takeIf { it.isNotBlank() }?.let { ".$it" } ?: ""
}

typealias Setting = String

interface Settings : Iterable<Setting> {
  operator fun get(key: String): Setting?
}

internal class SettingsImpl(private val p: Properties) : Settings {
  override fun get(key: String): Setting? = p[key].toString()

  override fun iterator(): Iterator<Setting> = p.propertyNames().toList()
    .map { p[it] }
    .map { it.toString() }
    .iterator()
}

fun settings(): Settings = SettingsImpl(Properties().apply { load(applicationProperties()) })
fun settingsWith(properties: Properties): Settings = SettingsImpl(properties)
