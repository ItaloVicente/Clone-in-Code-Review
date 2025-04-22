package com.couchbase.client.core.service;

import com.couchbase.client.core.ResponseEvent;
import com.couchbase.client.core.endpoint.Endpoint;
import com.couchbase.client.core.endpoint.view.ViewEndpoint;
import com.couchbase.client.core.env.CoreEnvironment;
import com.couchbase.client.core.service.strategies.RandomSelectionStrategy;
import com.couchbase.client.core.service.strategies.SelectionStrategy;
import com.lmax.disruptor.RingBuffer;

public class OldViewService extends AbstractPoolingService {

    private static final SelectionStrategy STRATEGY = new RandomSelectionStrategy();

    private static final EndpointFactory FACTORY = new ViewEndpointFactory();

    public OldViewService(final String hostname, final String bucket, final String password, final int port,
                       final CoreEnvironment env, final RingBuffer<ResponseEvent> responseBuffer) {
        super(hostname, bucket, password, port, env, env.viewEndpoints(), env.viewEndpoints(), STRATEGY, responseBuffer,
                FACTORY);
    }

    @Override
    public ServiceType type() {
        return ServiceType.VIEW;
    }

    static class ViewEndpointFactory implements EndpointFactory {
        @Override
        public Endpoint create(final String hostname, final String bucket, final String password, final int port,
                               final CoreEnvironment env, final RingBuffer<ResponseEvent> responseBuffer) {
            return new ViewEndpoint(hostname, bucket, password, port, env, responseBuffer);
        }
    }
}
