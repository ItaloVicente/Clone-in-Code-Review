package com.couchbase.client.core.service;

import com.couchbase.client.core.CouchbaseException;
import com.couchbase.client.core.ResponseEvent;
import com.couchbase.client.core.endpoint.Endpoint;
import com.couchbase.client.core.env.CoreEnvironment;
import com.couchbase.client.core.message.CouchbaseRequest;
import com.couchbase.client.core.message.internal.SignalFlush;
import com.couchbase.client.core.state.LifecycleState;
import com.lmax.disruptor.RingBuffer;
import rx.Subscriber;
import rx.functions.Action1;

import java.util.concurrent.atomic.AtomicReference;

public abstract class AbstractLazyService extends AbstractDynamicService {

    private final AtomicReference<Endpoint> storedEndpoint = new AtomicReference<Endpoint>();

    public AbstractLazyService(String hostname, String bucket, String password, int port, CoreEnvironment env,
        RingBuffer<ResponseEvent> responseBuffer, EndpointFactory endpointFactory) {
        super(hostname, bucket, password, port, env, 0, responseBuffer, endpointFactory);
    }

    @Override
    protected void dispatch(final CouchbaseRequest request) {
        if (storedEndpoint.get() == null) {
            final Endpoint newEndpoint = createEndpoint();
            if (storedEndpoint.compareAndSet(null, newEndpoint)) {
                endpointStates().register(newEndpoint, newEndpoint);
                newEndpoint
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
                            if (lifecycleState == LifecycleState.DISCONNECTED) {
                                request.observable().onError(new CouchbaseException("Could not connect storedEndpoint."));
                            }
                        }
                    });

                whenState(newEndpoint, LifecycleState.DISCONNECTED, new Action1<LifecycleState>() {
                    @Override
                    public void call(LifecycleState lifecycleState) {
                        endpointStates().deregister(newEndpoint);
                        storedEndpoint.set(null);
                    }
                });
            } else {
                newEndpoint.disconnect();
            }
        }

        sendAndFlushWhenConnected(storedEndpoint.get(), request);
    }

    private static void sendAndFlushWhenConnected(final Endpoint endpoint, final CouchbaseRequest request) {
        whenState(endpoint, LifecycleState.CONNECTED, new Action1<LifecycleState>() {
            @Override
            public void call(LifecycleState lifecycleState) {
                endpoint.send(request);
                endpoint.send(SignalFlush.INSTANCE);
            }
        });
    }

    Endpoint endpoint() {
        return storedEndpoint.get();
    }
}
