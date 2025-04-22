package com.couchbase.client.core.event.metric;

import java.util.List;

public class CoreNetworkLatencyMetricsEvent extends AbstractLatencyMetricsEvent {

    public CoreNetworkLatencyMetricsEvent(List<Metric> metrics) {
        super(metrics);
    }

}
