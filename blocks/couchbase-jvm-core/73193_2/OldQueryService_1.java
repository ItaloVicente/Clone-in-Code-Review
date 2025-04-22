package com.couchbase.client.core.service;

import com.couchbase.client.core.ResponseEvent;
import com.couchbase.client.core.endpoint.Endpoint;
import com.couchbase.client.core.endpoint.query.QueryEndpoint;
import com.couchbase.client.core.env.CoreEnvironment;
import com.couchbase.client.core.service.strategies.RandomSelectionStrategy;
import com.couchbase.client.core.service.strategies.RoundRobinSelectionStrategy;
import com.couchbase.client.core.service.strategies.SelectionStrategy;
import com.lmax.disruptor.RingBuffer;

public class OldQueryService extends AbstractPoolingService {

    private static final SelectionStrategy STRATEGY = new RoundRobinSelectionStrategy();

    private static final EndpointFactory FACTORY = new QueryEndpointFactory();

    public OldQueryService(final String hostname, final String bucket, final String password, final int port,
                        final CoreEnvironment env, final RingBuffer<ResponseEvent> responseBuffer) {
        super(hostname, bucket, password, port, env, env.queryEndpoints(), env.queryEndpoints(), STRATEGY,
                responseBuffer, FACTORY);
    }


    @Override
    public ServiceType type() {
        return ServiceType.QUERY;
    }

    static class QueryEndpointFactory implements EndpointFactory {
        @Override
        public Endpoint create(final String hostname, final String bucket, final String password, final int port,
                               final CoreEnvironment env, final RingBuffer<ResponseEvent> responseBuffer) {
            return new QueryEndpoint(hostname, bucket, password, port, env, responseBuffer);
        }
    }
}
