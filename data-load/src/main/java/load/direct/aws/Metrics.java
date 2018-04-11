package load.direct.aws;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;

import java.util.concurrent.TimeUnit;

final class Metrics {

    public static final MetricRegistry metricRegistry = new MetricRegistry();

    static {
        ConsoleReporter reporter = ConsoleReporter.forRegistry(metricRegistry)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .build();
        reporter.start(5, TimeUnit.SECONDS);
    }

    static final Meter inserts = metricRegistry.meter("inserts");
    static final Meter succeededInserts = metricRegistry.meter("succeeded-inserts");
    static final Meter failedInserts = metricRegistry.meter("failed-inserts");
    static final Meter succeededExecutions = metricRegistry.meter("succeeded-executions");

    static final Meter failedExecutions = metricRegistry.meter("failed-executions");
}
