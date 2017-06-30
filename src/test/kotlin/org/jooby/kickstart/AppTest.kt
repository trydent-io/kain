package org.jooby.kickstart

import com.mashape.unirest.http.Unirest
import org.jooby.test.JoobyRule
import org.junit.ClassRule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

private val LOCALHOST = "http://localhost:8080"

class AppTest {
  companion object {
    @ClassRule @JvmField
    var rule = JoobyRule(App())
  }

  @Test
  fun getHelloWorld() {
    val response = Unirest.get(LOCALHOST).asString()

    assertNotNull(response)
    assertEquals("Hello World!", response.body)
  }
}
