import com.datastax.driver.core.querybuilder.QueryBuilder
import com.datastax.driver.core.querybuilder.QueryBuilder.bindMarker
import com.datastax.driver.core.{BatchStatement, PreparedStatement, Session}

object CassandraRepository {

  def bind(session: Session): PreparedStatement = {
    val statement = QueryBuilder.insertInto("infare", "prices2")
      .value("week", bindMarker())
      .value("weeks_bef", bindMarker())
      .value("c_id", bindMarker())
      .value("class", bindMarker())
      .value("site", bindMarker())
      .value("one_way", bindMarker())
      .value("orig", bindMarker())
      .value("dest", bindMarker())
      .value("out_dep_dte", bindMarker())
      .value("out_dep_time", bindMarker())
      .value("out_sec_cnt", bindMarker())
      .value("id", bindMarker())
      .value("trip_price_avg_2", bindMarker())

    session.prepare(statement)
  }

  def save(session: Session,
           statement: PreparedStatement,
           tripsWithConvertedPrices: Seq[TripWithConvertedPrice]): Unit = {

    val batches = tripsWithConvertedPrices
      .grouped(2000)
      .map(singleBatch =>
        singleBatch.map(x => {
          statement
            .bind(x.week,
              x.weeksBefore,
              x.carrier,
              x.bookingClass,
              x.bookingSite,
              x.isOneWay,
              x.origin,
              x.destination,
              x.outboundDepartureDate,
              x.outboundDepartureTime,
              x.outboundSectorCount,
              x.id,
              x.convertedPrice)
        }))

    batches.foreach { statements =>
      import scala.collection.JavaConversions._

      Metrics.rows.mark(statements.size)

      val batch = new BatchStatement(BatchStatement.Type.UNLOGGED)
      batch.addAll(statements)
      session.execute(batch)
    }
  }

}
