package com.couchbase.client.core.service;

import com.couchbase.client.core.ResponseEvent;
import com.couchbase.client.core.endpoint.Endpoint;
import com.couchbase.client.core.env.CoreEnvironment;
import com.couchbase.client.core.message.CouchbaseRequest;
import com.couchbase.client.core.message.internal.SignalFlush;
import com.couchbase.client.core.state.LifecycleState;
import com.lmax.disruptor.RingBuffer;
import rx.functions.Action1;
import rx.functions.Func1;

public abstract class AbstractOnDemandService extends AbstractDynamicService {

    protected AbstractOnDemandService(String hostname, String bucket, String password, int port, CoreEnvironment env,
        RingBuffer<ResponseEvent> responseBuffer, EndpointFactory endpointFactory) {
        super(hostname, bucket, password, port, env, 0, responseBuffer, endpointFactory);
    }

    @Override
    protected void dispatch(final CouchbaseRequest request) {
        final Endpoint endpoint = createEndpoint();

        endpoint.connect();

        endpoint
            .states()
            .filter(new Func1<LifecycleState, Boolean>() {
                @Override
                public Boolean call(LifecycleState state) {
                    return state == LifecycleState.CONNECTED;
                }
            })
            .take(1)
            .subscribe(new Action1<LifecycleState>() {
                @Override
                public void call(LifecycleState lifecycleState) {
                    endpoint.send(request);
                    endpoint.send(SignalFlush.INSTANCE);
                }
            });
    }
}
