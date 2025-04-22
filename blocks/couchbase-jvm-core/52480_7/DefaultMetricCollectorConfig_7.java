package com.couchbase.client.core.metrics;

import com.couchbase.client.core.event.CouchbaseEvent;
import com.couchbase.client.core.event.EventBus;
import com.couchbase.client.core.event.metric.AbstractLatencyMetricsEvent;
import com.couchbase.client.core.event.metric.CoreNetworkLatencyMetricsEvent;
import rx.Scheduler;

import java.util.List;

public class CoreNetworkLatencyMetricCollector extends AbstractLatencyMetricCollector {

    public CoreNetworkLatencyMetricCollector(EventBus eventBus, Scheduler scheduler, MetricCollectorConfig config) {
        super(eventBus, scheduler, config);
    }

    @Override
    protected CouchbaseEvent generateEvent(List<AbstractLatencyMetricsEvent.Metric> metrics) {
        return new CoreNetworkLatencyMetricsEvent(metrics);
    }
}
