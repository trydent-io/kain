package srl.paros.kain.api

import java.util.Properties

private const val PROPERTIES = "application.properties"

private fun applicationProperties() = Setup::class.java.getResourceAsStream("application.properties")

data class Name(private val name: String) {
  val property get() = name.takeIf { it.isNotBlank() }?.let { ".$it" } ?: ""
}

data class Setting(private val s: String) {
  val value get():String = s
}

interface Setup : Iterable<Setting> {
  operator fun get(key: String): Setting?
}

internal class PlainSetup(p: Properties) : Setup {
  private val properties = p

  override fun get(key: String): Setting? = properties[key]
    .let(Any?::toString)
    .let(::Setting)

  override fun iterator(): Iterator<Setting> = properties
    .propertyNames()
    .toList()
    .map { properties[it].toString() }
    .map(::Setting)
    .iterator()
}

fun setup(): Setup = Properties().apply { load(applicationProperties()) }.let(::PlainSetup)
fun setupWith(p: Properties): Setup = PlainSetup(p)
