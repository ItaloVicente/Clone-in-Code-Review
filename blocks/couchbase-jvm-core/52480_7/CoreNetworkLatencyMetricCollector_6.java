package com.couchbase.client.core.metrics;

import com.couchbase.client.core.event.CouchbaseEvent;
import com.couchbase.client.core.event.EventBus;
import com.couchbase.client.core.event.metric.AbstractLatencyMetricsEvent;
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

public abstract class AbstractLatencyMetricCollector implements LatencyMetricCollector {

    private static final CouchbaseLogger LOGGER = CouchbaseLoggerFactory.getInstance(MetricCollector.class);

    private final MetricCollectorConfig config;
    private final Map<Object, LatencyStats> latencyMetrics;

    protected abstract CouchbaseEvent generateEvent(List<AbstractLatencyMetricsEvent.Metric> metrics);

    public AbstractLatencyMetricCollector(final EventBus eventBus, final Scheduler scheduler,
        final MetricCollectorConfig config) {
        this.config = config;
        latencyMetrics = new ConcurrentHashMap<Object, LatencyStats>();

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
    public void recordLatency(final Object identifier, final long latency) {
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

    private CouchbaseEvent prepareEvent() {
        TimeUnit targetUnit = config.targetUnit();

        List<AbstractLatencyMetricsEvent.Metric> metrics = new ArrayList<AbstractLatencyMetricsEvent.Metric>();
        for (Map.Entry<Object, LatencyStats> metric : latencyMetrics.entrySet()) {
            Histogram histogram = metric.getValue().getIntervalHistogram();

            if (histogram.getTotalCount() == 0) {
                latencyMetrics.remove(metric.getKey());
                continue;
            }

            metrics.add(new AbstractLatencyMetricsEvent.Metric(
                    metric.getKey(),
                    targetUnit.convert(histogram.getMinValue(), TimeUnit.NANOSECONDS),
                    targetUnit.convert(histogram.getMaxValue(), TimeUnit.NANOSECONDS),
                    histogram.getTotalCount(),
                    targetUnit.convert(histogram.getValueAtPercentile(50.0), TimeUnit.NANOSECONDS),
                    targetUnit.convert(histogram.getValueAtPercentile(90.0), TimeUnit.NANOSECONDS),
                    targetUnit.convert(histogram.getValueAtPercentile(95.0), TimeUnit.NANOSECONDS),
                    targetUnit.convert(histogram.getValueAtPercentile(99.0), TimeUnit.NANOSECONDS),
                    targetUnit.convert(histogram.getValueAtPercentile(99.9), TimeUnit.NANOSECONDS)
            ));
        }
        return generateEvent(metrics);
    }
}
