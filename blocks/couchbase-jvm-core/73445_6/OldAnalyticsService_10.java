package com.couchbase.client.core.service;

import com.couchbase.client.core.ResponseEvent;
import com.couchbase.client.core.endpoint.Endpoint;
import com.couchbase.client.core.endpoint.analytics.AnalyticsEndpoint;
import com.couchbase.client.core.endpoint.query.QueryEndpoint;
import com.couchbase.client.core.env.CoreEnvironment;
import com.couchbase.client.core.service.strategies.RoundRobinSelectionStrategy;
import com.couchbase.client.core.service.strategies.SelectionStrategy;
import com.lmax.disruptor.RingBuffer;

public class OldAnalyticsService extends AbstractPoolingService {

    private static final SelectionStrategy STRATEGY = new RoundRobinSelectionStrategy();

    private static final EndpointFactory FACTORY = new AnalyticsEndpointFactory();

    public OldAnalyticsService(final String hostname, final String bucket, final String password, final int port,
        final CoreEnvironment env, final RingBuffer<ResponseEvent> responseBuffer) {
        super(hostname, bucket, password, port, env, env.queryEndpoints(), env.queryEndpoints(), STRATEGY,
                responseBuffer, FACTORY);
    }


    @Override
    public ServiceType type() {
        return ServiceType.ANALYTICS;
    }

    static class AnalyticsEndpointFactory implements EndpointFactory {
        @Override
        public Endpoint create(final String hostname, final String bucket, final String password, final int port,
            final CoreEnvironment env, final RingBuffer<ResponseEvent> responseBuffer) {
            return new AnalyticsEndpoint(hostname, bucket, password, port, env, responseBuffer);
        }
    }
}
