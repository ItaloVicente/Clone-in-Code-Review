
package com.couchbase.client.core.service;

import com.couchbase.client.core.ResponseEvent;
import com.couchbase.client.core.endpoint.Endpoint;
import com.couchbase.client.core.endpoint.dcp.DCPEndpoint;
import com.couchbase.client.core.env.CoreEnvironment;
import com.couchbase.client.core.service.strategies.FirstConnectedSelectionStrategy;
import com.couchbase.client.core.service.strategies.SelectionStrategy;
import com.lmax.disruptor.RingBuffer;

public class DCPService extends AbstractService {
    private static final SelectionStrategy STRATEGY = new FirstConnectedSelectionStrategy();

    private static final EndpointFactory FACTORY = new DCPEndpointFactory();

    private static final int NUM_ENDPOINTS = 1;

    public DCPService(String hostname, String bucket, String password, int port,
                      CoreEnvironment env, RingBuffer<ResponseEvent> responseBuffer) {
        super(hostname, bucket, password, port, env, NUM_ENDPOINTS, STRATEGY, responseBuffer, FACTORY);
    }

    @Override
    public ServiceType type() {
        return ServiceType.DCP;
    }

    static class DCPEndpointFactory implements EndpointFactory {
        @Override
        public Endpoint create(final String hostname, final String bucket, final String password, final int port,
                               final CoreEnvironment env, final RingBuffer<ResponseEvent> responseBuffer) {
            return new DCPEndpoint(hostname, bucket, password, port, env, responseBuffer);
        }
    }
}
