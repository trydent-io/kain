package srl.paros.kain.db

import com.github.davidmoten.rx.jdbc.Database
import javax.sql.DataSource

interface RxDbSource : DbSource {
  fun database(): Database
  fun databaseAsync(): Database
}

internal class RxDbSourceImpl(private val dbSource: DbSource) : RxDbSource {
  override fun dataSource(): DataSource = dbSource.dataSource()

  override fun database(): Database = Database.fromDataSource(dataSource())

  override fun databaseAsync(): Database = database().asynchronous()
}

fun rxDbSource(dbSource: DbSource): RxDbSource = RxDbSourceImpl(dbSource)
fun rxDbSource(url: String, usr: String, pwd: String, drv: String): RxDbSource = rxDbSource(dbSource(url, usr, pwd, drv))
