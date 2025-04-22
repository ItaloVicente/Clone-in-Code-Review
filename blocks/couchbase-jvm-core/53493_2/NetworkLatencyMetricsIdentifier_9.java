package com.couchbase.client.core.metrics;

import com.couchbase.client.core.event.EventBus;
import com.couchbase.client.core.event.metrics.LatencyMetric;
import com.couchbase.client.core.event.metrics.NetworkLatencyMetricsEvent;
import org.HdrHistogram.Histogram;
import org.LatencyUtils.LatencyStats;
import rx.Scheduler;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class NetworkLatencyMetricsCollector
    extends AbstractLatencyMetricsCollector<NetworkLatencyMetricsIdentifier, NetworkLatencyMetricsEvent> {

    private final TimeUnit targetUnit;
    private final Double[] targetPercentiles;

    public NetworkLatencyMetricsCollector(EventBus eventBus, Scheduler scheduler, LatencyMetricsCollectorConfig config) {
        super(eventBus, scheduler, config);

        targetUnit = config.targetUnit();
        targetPercentiles = config.targetPercentiles();
    }

    @Override
    protected NetworkLatencyMetricsEvent generateLatencyMetricsEvent(
        final Map<NetworkLatencyMetricsIdentifier, LatencyStats> latencyMetrics) {

        Map<NetworkLatencyMetricsIdentifier, LatencyMetric> sortedMetrics =
            new TreeMap<NetworkLatencyMetricsIdentifier, LatencyMetric>();


        for (Map.Entry<NetworkLatencyMetricsIdentifier, LatencyStats> entry : latencyMetrics.entrySet()) {
            Histogram histogram = entry.getValue().getIntervalHistogram();

            if (histogram.getTotalCount() == 0) {
                remove(entry.getKey());
                continue;
            }

            Map<Double, Long> percentiles = new TreeMap<Double, Long>();
            for (double targetPercentile : targetPercentiles) {
                percentiles.put(targetPercentile, targetUnit.convert(
                    histogram.getValueAtPercentile(targetPercentile), TimeUnit.NANOSECONDS)
                );
            }

            sortedMetrics.put(entry.getKey(), new LatencyMetric(
                targetUnit.convert(histogram.getMinValue(), TimeUnit.NANOSECONDS),
                targetUnit.convert(histogram.getMaxValue(), TimeUnit.NANOSECONDS),
                histogram.getTotalCount(),
                percentiles
            ));
        }

        return new NetworkLatencyMetricsEvent(sortedMetrics);
    }
}
