
package com.couchbase.client.core.service;

import com.couchbase.client.core.ResponseEvent;
import com.couchbase.client.core.endpoint.Endpoint;
import com.couchbase.client.core.endpoint.kv.KeyValueEndpoint;
import com.couchbase.client.core.env.CoreEnvironment;
import com.couchbase.client.core.service.strategies.PartitionSelectionStrategy;
import com.couchbase.client.core.service.strategies.SelectionStrategy;
import com.lmax.disruptor.RingBuffer;

public class OldKeyValueService extends AbstractPoolingService {

    private static final SelectionStrategy STRATEGY = PartitionSelectionStrategy.INSTANCE;

    private static final EndpointFactory FACTORY = new KeyValueEndpointFactory();

    public OldKeyValueService(final String hostname, final String bucket, final String password, final int port,
                           final CoreEnvironment env, final RingBuffer<ResponseEvent> responseBuffer) {
        super(hostname, bucket, password, port, env, env.kvEndpoints(), env.kvEndpoints(), STRATEGY, responseBuffer,
                FACTORY);
    }

    @Override
    public ServiceType type() {
        return ServiceType.BINARY;
    }

    static class KeyValueEndpointFactory implements EndpointFactory {
        @Override
        public Endpoint create(String hostname, String bucket, String password, int port, CoreEnvironment env,
                               RingBuffer<ResponseEvent> responseBuffer) {
            return new KeyValueEndpoint(hostname, bucket, password, port, env, responseBuffer);
        }
    }
}
