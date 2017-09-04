package srl.paros.kain.http

import io.restassured.RestAssured.given
import io.undertow.util.Headers.CONTENT_TYPE_STRING
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.hamcrest.core.Is.`is` as fits

class DefaultHttpServerTest {
  private val hs = DefaultHttpServer(LOCALHOST, Port(9080)).get()

  @Before
  fun before() = hs.start()

  @After
  fun after() = hs.stop()

  @Test
  fun when_root_then_200() {
    given()
      .port(9080)
      .`when`()
      .get("/hello")
      .then()
      .statusCode(200)
  }

  @Test
  fun when_root_then_hello_world() {
    given()
      .port(9080)
      .`when`()
      .get("/hello")
      .then()
      .header(CONTENT_TYPE_STRING, "text/plain")
      .body(fits("Hello Blockchain"))
  }
}
