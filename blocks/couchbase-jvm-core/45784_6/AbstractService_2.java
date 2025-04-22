package com.couchbase.client.core.service;

import com.couchbase.client.core.ResponseEvent;
import com.couchbase.client.core.ResponseHandler;
import com.couchbase.client.core.endpoint.Endpoint;
import com.couchbase.client.core.env.CoreEnvironment;
import com.couchbase.client.core.message.CouchbaseRequest;
import com.couchbase.client.core.service.strategies.SelectionStrategy;
import com.lmax.disruptor.RingBuffer;

public abstract class AbstractPoolingService extends AbstractDynamicService {

    private final int maxEndpoints;
    private final RingBuffer<ResponseEvent> responseBuffer;
    private final SelectionStrategy strategy;

    protected AbstractPoolingService(String hostname, String bucket, String password, int port,
        CoreEnvironment env, int minEndpoints, int maxEndpoints, SelectionStrategy strategy,
        RingBuffer<ResponseEvent> responseBuffer, EndpointFactory endpointFactory) {
        super(hostname, bucket, password, port, env, minEndpoints, responseBuffer, endpointFactory);
        this.maxEndpoints = maxEndpoints;
        this.responseBuffer = responseBuffer;
        this.strategy = strategy;
    }

    @Override
    protected void dispatch(final CouchbaseRequest request) {
        if (endpoints().length == maxEndpoints) {
            Endpoint endpoint = strategy.select(request, endpoints());
            if (endpoint == null) {
                responseBuffer.publishEvent(ResponseHandler.RESPONSE_TRANSLATOR, request, request.observable());
            } else {
                endpoint.send(request);
            }
        } else {
            throw new UnsupportedOperationException("Dynamic endpoint scaling is currently not supported.");
        }
    }

}
