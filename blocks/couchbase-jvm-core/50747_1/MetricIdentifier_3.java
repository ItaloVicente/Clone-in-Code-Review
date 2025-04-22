package com.couchbase.client.core.metrics;

import com.couchbase.client.core.event.EventBus;
import com.couchbase.client.core.event.metrics.LatencyMetricsEvent;
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

public class DefaultMetricsCollector implements MetricsCollector {

    private final Map<MetricIdentifier, LatencyStats> latencyMetrics;

    public DefaultMetricsCollector(final EventBus eventBus, final int emitFrequency, final TimeUnit emitUnit,
        final TimeUnit targetUnit, final Scheduler scheduler) {
        latencyMetrics = new ConcurrentHashMap<MetricIdentifier, LatencyStats>();

        Observable
            .interval(emitFrequency, emitUnit, scheduler)
            .subscribe(new Action1<Long>() {
                @Override
                public void call(Long ignored) {
                    if (eventBus.hasSubscribers()) {
                        List<LatencyMetricsEvent.Metric> metrics = new ArrayList<LatencyMetricsEvent.Metric>();
                        for (Map.Entry<MetricIdentifier, LatencyStats> metric : latencyMetrics.entrySet()) {
                            Histogram histogram = metric.getValue().getIntervalHistogram();
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
                        eventBus.publish(new LatencyMetricsEvent(metrics));
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

}
