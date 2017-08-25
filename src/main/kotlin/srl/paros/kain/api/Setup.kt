package srl.paros.kain.core

import java.util.Properties
import javax.sql.DataSource

private const val PROPERTIES = "application.properties"

private fun applicationProperties() = Setup::class.java.getResourceAsStream("application.properties")

data class Name(private val name: String) {
  val property get() = name.takeIf { it.isNotBlank() }?.let { ".$it" } ?: ""
}

interface Setup<out T> : Iterable<String> {
  operator fun get(name: Name = Name("")): T
}
