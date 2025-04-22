package com.couchbase.client.core.event.metrics;

import com.couchbase.client.core.metrics.NetworkLatencyMetricsIdentifier;

import java.util.Map;

public class NetworkLatencyMetricsEvent extends LatencyMetricsEvent<NetworkLatencyMetricsIdentifier> {

    public NetworkLatencyMetricsEvent(Map<NetworkLatencyMetricsIdentifier, LatencyMetric> latencies) {
        super(latencies);
    }

}
