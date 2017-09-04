package srl.paros.kain.db

import org.junit.Test
import kotlin.test.assertNotNull

private const val url = "test"

class DbTest {
  private val db = CachedDbs.H2_FILE.db

  @Test
  fun when_open_then_not_null() = assertNotNull(db.open("test.db"))

}
