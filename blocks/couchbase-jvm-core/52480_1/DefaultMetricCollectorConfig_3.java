package com.couchbase.client.core.metrics;

import com.couchbase.client.core.event.EventBus;
import com.couchbase.client.core.event.metric.LatencyMetricsEvent;
import com.couchbase.client.core.logging.CouchbaseLogger;
import com.couchbase.client.core.logging.CouchbaseLoggerFactory;
import org.HdrHistogram.Histogram;
import org.LatencyUtils.LatencyStats;
import rx.Observable;
import rx.Scheduler;
import rx.functions.Action1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class DefaultMetricCollector implements MetricCollector {

    private static final CouchbaseLogger LOGGER = CouchbaseLoggerFactory.getInstance(MetricCollector.class);

    private final EventBus eventBus;
    private final MetricCollectorConfig config;

    private final Map<MetricIdentifier, LatencyStats> latencyMetrics;

    public DefaultMetricCollector(final EventBus eventBus, final Scheduler scheduler, final MetricCollectorConfig config) {
        this.eventBus = eventBus;
        this.config = config;
        latencyMetrics = new ConcurrentHashMap<MetricIdentifier, LatencyStats>();

        Observable
            .interval(config.emitFrequency(), config.emitFrequencyUnit(), scheduler)
            .subscribe(new Action1<Long>() {
                @Override
                public void call(Long ignored) {
                    try {
                        if (eventBus.hasSubscribers()) {
                            eventBus.publish(prepareEvent());
                        }
                    } catch (Exception ex) {
                        LOGGER.warn("Caught exception while publishing to event bus.", ex);
                    }
                }
            });
    }

    @Override
    public void recordLatency(final MetricIdentifier identifier, final long latency) {
        LatencyStats metric = latencyMetrics.get(identifier);
        if (metric == null) {
            metric = new LatencyStats();
            latencyMetrics.put(identifier, metric);
        }
        metric.recordLatency(latency);
    }

    @Override
    public MetricCollectorConfig config() {
        return config;
    }

    private LatencyMetricsEvent prepareEvent() {
        TimeUnit targetUnit = config.targetUnit();

        List<LatencyMetricsEvent.Metric> metrics = new ArrayList<LatencyMetricsEvent.Metric>();
        for (Map.Entry<MetricIdentifier, LatencyStats> metric : latencyMetrics.entrySet()) {
            Histogram histogram = metric.getValue().getIntervalHistogram();

            if (histogram.getTotalCount() == 0) {
                latencyMetrics.remove(metric.getKey());
                continue;
            }

            metrics.add(new LatencyMetricsEvent.Metric(
                    metric.getKey().toString(),
                    targetUnit.convert(histogram.getMinValue(), TimeUnit.NANOSECONDS),
                    targetUnit.convert(histogram.getMaxValue(), TimeUnit.NANOSECONDS),
                    histogram.getTotalCount(),
                    targetUnit.convert(histogram.getValueAtPercentile(99.0), TimeUnit.NANOSECONDS),
                    targetUnit.convert(histogram.getValueAtPercentile(99.9), TimeUnit.NANOSECONDS),
                    targetUnit.convert(histogram.getValueAtPercentile(99.99), TimeUnit.NANOSECONDS),
                    targetUnit.convert(histogram.getValueAtPercentile(99.999), TimeUnit.NANOSECONDS)
            ));
        }
        return new LatencyMetricsEvent(metrics);
    }
}
