package srl.paros.kain.db

import com.github.davidmoten.rx.jdbc.Database
import srl.paros.kain.DbSource
import srl.paros.kain.RxDbSource
import java.util.*
import javax.sql.DataSource

internal class RxDbSourceImpl(private val dbSource: DbSource) : RxDbSource {
  override fun dataSource(): DataSource = dbSource.dataSource()

  override fun database(): Database = Database.fromDataSource(dataSource())

  override fun databaseAsync(): Database = database().asynchronous()
}

fun RxDbSourceWith(properties: Properties): RxDbSource = RxDbSourceImpl(
  HikariDbSource(
    HikariDbSettingsWith(properties)
  )
)
