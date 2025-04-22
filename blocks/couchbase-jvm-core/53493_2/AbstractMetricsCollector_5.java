package com.couchbase.client.core.metrics;

import com.couchbase.client.core.event.CouchbaseEvent;
import com.couchbase.client.core.event.EventBus;
import com.couchbase.client.core.event.metrics.LatencyMetricsEvent;
import org.LatencyUtils.LatencyStats;
import rx.Scheduler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractLatencyMetricsCollector<I extends LatencyMetricsIdentifier, E extends LatencyMetricsEvent>
    extends AbstractMetricsCollector
    implements LatencyMetricsCollector<I> {

    private final Map<I, LatencyStats> latencyMetrics;
    private final LatencyMetricsCollectorConfig config;

    protected AbstractLatencyMetricsCollector(EventBus eventBus, Scheduler scheduler, LatencyMetricsCollectorConfig config) {
        super(eventBus, scheduler, config);
        this.config = config;
        latencyMetrics = new ConcurrentHashMap<I, LatencyStats>();

    }

    protected abstract E generateLatencyMetricsEvent(Map<I, LatencyStats> latencyMetrics);

    @Override
    protected CouchbaseEvent generateCouchbaseEvent() {
        return generateLatencyMetricsEvent(new HashMap<I, LatencyStats>(latencyMetrics));
    }

    @Override
    public void record(I identifier, long latency) {
        if (config.emitFrequency() <= 0) {
            return;
        }

        LatencyStats metric = latencyMetrics.get(identifier);
        if (metric == null) {
            metric = new LatencyStats();
            latencyMetrics.put(identifier, metric);
        }
        metric.recordLatency(latency);
    }

    @Override
    public LatencyMetricsCollectorConfig config() {
        return config;
    }

    protected void remove(I identifier) {
        latencyMetrics.remove(identifier);
    }

}
