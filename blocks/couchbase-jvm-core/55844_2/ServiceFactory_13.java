
package com.couchbase.client.core.service;

import com.couchbase.client.core.ResponseEvent;
import com.couchbase.client.core.endpoint.Endpoint;
import com.couchbase.client.core.endpoint.search.SearchEndpoint;
import com.couchbase.client.core.env.CoreEnvironment;
import com.couchbase.client.core.service.strategies.RandomSelectionStrategy;
import com.couchbase.client.core.service.strategies.SelectionStrategy;
import com.lmax.disruptor.RingBuffer;

public class SearchService extends AbstractPoolingService {
    private static final SelectionStrategy STRATEGY = new RandomSelectionStrategy();
    private static final EndpointFactory FACTORY = new SearchEndpointFactory();

    public SearchService(final String hostname, final String bucket, final String password, final int port,
                         final CoreEnvironment env, final RingBuffer<ResponseEvent> responseBuffer) {
        super(hostname, bucket, password, port, env, env.searchEndpoints(), env.searchEndpoints(), STRATEGY,
                responseBuffer, FACTORY);
    }

    @Override
    public ServiceType type() {
        return ServiceType.SEARCH;
    }

    static class SearchEndpointFactory implements EndpointFactory {
        @Override
        public Endpoint create(final String hostname, final String bucket, final String password, final int port,
                               final CoreEnvironment env, final RingBuffer<ResponseEvent> responseBuffer) {
            return new SearchEndpoint(hostname, bucket, password, port, env, responseBuffer);
        }
    }

}
