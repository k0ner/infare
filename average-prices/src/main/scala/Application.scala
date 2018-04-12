import com.datastax.spark.connector._
import com.datastax.spark.connector.cql.CassandraConnector
import org.apache.spark.internal.Logging
import org.apache.spark.{SparkConf, SparkContext}


object Application extends App with Logging {

  val conf = new SparkConf(true)
    .set("spark.cassandra.connection.host", "localhost")
    .set("spark.cassandra.auth.username", "cassandra")
    .set("spark.cassandra.auth.password", "cassandra")
    .set("spark.cassandra.output.batch.size.rows", "200")
    .set("spark.master", "local[4]")
    .setAppName("test")

  val sc = new SparkContext(conf)
  val cc = CassandraConnector(sc.getConf)

  sc.cassandraTable("infare", "prices")
    .spanBy(row => row.getInt("week"))
    .foreachPartition { manyWeeksIterator =>
      val fxService = FxServiceFactory.getOrCreate()
      cc.withSessionDo { session =>
        val preparedStatement = CassandraRepository.bind(session)

        manyWeeksIterator.foreach { weekIterator =>
          val week = weekIterator._1
          val tripsWithConvertedPrices = weekIterator._2.map { singlePriceRow =>
            TripWithConvertedPrice.apply(
              week,
              singlePriceRow.getInt("weeks_bef"),
              singlePriceRow.getInt("c_id"),
              singlePriceRow.getString("class"),
              singlePriceRow.getInt("site"),
              singlePriceRow.getBoolean("one_way"),
              singlePriceRow.getInt("orig"),
              singlePriceRow.getInt("dest"),
              singlePriceRow.getInt("out_dep_dte"),
              singlePriceRow.getInt("out_dep_time"),
              singlePriceRow.getInt("out_sec_cnt"),
              fxService.getPrice(singlePriceRow.getDecimal("price_avg")))
          }

          log.info(s"preparing to save data for $week")
          CassandraRepository.save(session, preparedStatement, tripsWithConvertedPrices.toSeq)
          log.info(s"data saved for $week")
        }
      }
    }

  System.in.read()
}
