package com.couchbase.client.core.service;

import com.couchbase.client.core.ResponseEvent;
import com.couchbase.client.core.endpoint.Endpoint;
import com.couchbase.client.core.env.CoreEnvironment;
import com.couchbase.client.core.message.CouchbaseRequest;
import com.couchbase.client.core.message.internal.SignalFlush;
import com.couchbase.client.core.state.LifecycleState;
import com.lmax.disruptor.RingBuffer;
import rx.Subscriber;
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
        endpointStates().register(endpoint, endpoint);

        endpoint
            .connect()
            .subscribe(new Subscriber<LifecycleState>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                    request.observable().onError(e);
                }

                @Override
                public void onNext(LifecycleState lifecycleState) {
                }
            });

        whenState(endpoint, LifecycleState.CONNECTED, new Action1<LifecycleState>() {
            @Override
            public void call(LifecycleState lifecycleState) {
                endpoint.send(request);
                endpoint.send(SignalFlush.INSTANCE);
            }
        });

        whenState(endpoint, LifecycleState.DISCONNECTED, new Action1<LifecycleState>() {
            @Override
            public void call(LifecycleState lifecycleState) {
                endpointStates().deregister(endpoint);
            }
        });
    }

    private void whenState(Endpoint endpoint, final LifecycleState wanted, Action1<LifecycleState> then) {
        endpoint
            .states()
            .filter(new Func1<LifecycleState, Boolean>() {
                @Override
                public Boolean call(LifecycleState state) {
                    return state == wanted;
                }
            })
            .take(1)
            .subscribe(then);
    }
}
