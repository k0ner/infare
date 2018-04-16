import org.apache.spark.internal.Logging
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

object SqlApplication extends App with Logging {

  val conf = new SparkConf(true)
    .set("spark.cassandra.connection.host", "localhost")
    .set("spark.cassandra.auth.username", "cassandra")
    .set("spark.cassandra.auth.password", "cassandra")
    .set("spark.cassandra.output.batch.size.rows", "200")
    .set("spark.master", "local[10]")
    .setAppName("test")

  val sc = new SparkContext(conf)

  val sqlContext = SparkSession.builder()
    .appName("test")
    .getOrCreate()

  import org.apache.spark.sql.functions._
  import sqlContext.implicits._

  val df = sqlContext
    .read
    .format("org.apache.spark.sql.cassandra")
    .options(Map("table" -> "prices2", "keyspace" -> "infare"))
    .load()

  //Task 1
  def task1() = {
    val rows = df.count()
    val uniqueRows = df
      .select($"week",
        $"weeks_bef",
        $"c_id",
        $"class",
        $"one_way",
        $"orig",
        $"dest",
        $"out_dep_dte",
        $"out_dep_time",
        $"out_sec_cnt",
        $"hm_dep_dte",
        $"hm_dep_time",
        $"hm_sec_cnt",
        $"out_sec_1",
        $"out_sec_2",
        $"out_sec_3")
      .distinct()
      .count()

    println(s"Total $rows")
    println(s"Unique $uniqueRows")
    println(s"Duplicates ${rows - uniqueRows}")
  }

  // Task 2
  def task2() = {
    df.select("one_way")
      .groupBy("one_way")
      .count()
      .show()
  }

  // Task 3
  def task3() = {
    df
      .filter($"one_way".equalTo(true).and($"out_sec_cnt".equalTo(1)))
      .groupBy($"c_id", $"class", $"orig", $"dest", $"out_dep_dte", $"out_dep_time")
      .agg(sum($"agg_cnt").as("agg"))
      .sort($"agg".desc)
      .show(100)
  }

  // Task 4
  def task4() = {
    df.filter($"one_way".equalTo(true))
      .filter($"out_sec_cnt".equalTo(1))
      .sortWithinPartitions($"agg_cnt".desc)
      .sort($"agg_cnt".desc)
      .sample(withReplacement = true, 0.01)
      .limit(100000)
      .rdd
      .saveAsTextFile("/tmp/samples.csv")
  }

  // Task 6
  def task5() = {
    df.groupBy($"orig")
      .agg(avg($"price_avg"))
      .show(1000)
  }

  // Task 7
  //  val original = df.as("original")
  //
  //  val filtered = df.sort(($"price_max"-$"price_min").desc)
  //    .limit(1).as("filtered")
  //
  //    filtered
  //      .join(original, original("orig").equalTo(filtered("orig")))
  //    .show()
}
