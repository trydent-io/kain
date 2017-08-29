package srl.paros.kain.db

import com.github.davidmoten.rx.jdbc.Database
import com.github.davidmoten.rx.jdbc.Database.fromDataSource
import javax.sql.DataSource

interface RxDb : Db {
  fun poolRx(url: String, usr: String = "", pwd: String = ""): Database
}

internal class SimpleRxDb(private val db: Db) : RxDb {
  override fun poolRx(url: String, usr: String, pwd: String): Database = fromDataSource(pool(url, usr, pwd))

  override fun pool(url: String, usr: String, pwd: String): DataSource = db.pool(url, usr, pwd)
}

fun rxH2InFile(): RxDb = SimpleRxDb(h2InFile())
