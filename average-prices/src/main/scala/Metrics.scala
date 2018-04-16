import java.util.concurrent.TimeUnit

import com.codahale.metrics.{ConsoleReporter, MetricRegistry}

object Metrics {

  private val metricRegistry = new MetricRegistry

  val rows = metricRegistry.meter("rows")
  val batchTime = metricRegistry.timer("batch-time")

  ConsoleReporter.forRegistry(metricRegistry).convertRatesTo(TimeUnit.SECONDS)
    .convertDurationsTo(TimeUnit.MILLISECONDS).build
    .start(5, TimeUnit.SECONDS)

}
