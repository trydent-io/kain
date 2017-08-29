package srl.paros.kain.db

import org.junit.Test
import kotlin.test.assertNotNull

private const val url = "test"

class DbTest {
  private val db = h2InFile()

  @Test
  fun when_source_then_not_null() = assertNotNull(db.pool(url))

  @Test
  fun when_connect_then_not_null() = assertNotNull(db.poolRx(url))
}
