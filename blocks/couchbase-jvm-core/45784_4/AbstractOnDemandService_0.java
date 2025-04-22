package com.couchbase.client.core.service;

import com.couchbase.client.core.ResponseEvent;
import com.couchbase.client.core.endpoint.Endpoint;
import com.couchbase.client.core.env.CoreEnvironment;
import com.couchbase.client.core.logging.CouchbaseLogger;
import com.couchbase.client.core.logging.CouchbaseLoggerFactory;
import com.couchbase.client.core.message.CouchbaseRequest;
import com.couchbase.client.core.message.internal.SignalFlush;
import com.couchbase.client.core.state.AbstractStateMachine;
import com.couchbase.client.core.state.LifecycleState;
import com.lmax.disruptor.RingBuffer;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public abstract class AbstractDynamicService extends AbstractStateMachine<LifecycleState> implements Service {

    private static final CouchbaseLogger LOGGER = CouchbaseLoggerFactory.getInstance(Service.class);

    private final Endpoint[] endpoints;

    private final String hostname;
    private final String bucket;
    private final String password;
    private final int port;
    private final CoreEnvironment env;
    private final RingBuffer<ResponseEvent> responseBuffer;
    private final int minEndpoints;
    private final EndpointFactory endpointFactory;
    private final EndpointStateZipper endpointStates;

    protected AbstractDynamicService(final String hostname, final String bucket, final String password, final int port,
        final CoreEnvironment env, final int minEndpoints,
        final RingBuffer<ResponseEvent> responseBuffer, final EndpointFactory endpointFactory) {
        super(LifecycleState.DISCONNECTED);
        this.hostname = hostname;
        this.bucket = bucket;
        this.password = password;
        this.port = port;
        this.env = env;
        this.minEndpoints = minEndpoints;
        this.responseBuffer = responseBuffer;
        this.endpointFactory = endpointFactory;
        endpointStates = new EndpointStateZipper();
        endpoints = new Endpoint[minEndpoints];
        endpointStates.states().subscribe(new Action1<LifecycleState>() {
            @Override
            public void call(LifecycleState lifecycleState) {
                transitionState(lifecycleState);
            }
        });
    }

    protected abstract void dispatch(CouchbaseRequest request);

    @Override
    public Observable<LifecycleState> connect() {
        LOGGER.debug(logIdent(hostname, this) + "Got instructed to connect.");
        if (state() == LifecycleState.CONNECTED || state() == LifecycleState.CONNECTING) {
            LOGGER.debug(logIdent(hostname, this) + "Already connected or connecting, skipping connect.");
            return Observable.just(state());
        }

        for (int i = 0; i < minEndpoints; i++) {
            Endpoint endpoint = createEndpoint();
            endpoints[i] = endpoint;
            endpointStates.register(endpoint, endpoint);
        }

        return Observable
            .from(endpoints)
            .flatMap(new Func1<Endpoint, Observable<LifecycleState>>() {
                @Override
                public Observable<LifecycleState> call(final Endpoint endpoint) {
                    LOGGER.debug(logIdent(hostname, AbstractDynamicService.this)
                            + "Initializing connect on Endpoint.");
                    return endpoint.connect();
                }
            })
            .lastOrDefault(LifecycleState.DISCONNECTED)
            .map(new Func1<LifecycleState, LifecycleState>() {
                @Override
                public LifecycleState call(final LifecycleState state) {
                    return state();
                }
            });
    }

    @Override
    public void send(final CouchbaseRequest request) {
        if (request instanceof SignalFlush) {
            int length = endpoints.length;
            for (int i = 0; i < length; i++) {
                endpoints[i].send(request);
            }
            return;
        }

        dispatch(request);
    }

    @Override
    public Observable<LifecycleState> disconnect() {
        LOGGER.debug(logIdent(hostname, this) + "Got instructed to disconnect.");
        if (state() == LifecycleState.DISCONNECTED || state() == LifecycleState.DISCONNECTING) {
            LOGGER.debug(logIdent(hostname, this) + "Already disconnected or disconnecting, skipping disconnect.");
            return Observable.just(state());
        }

        return Observable
                .from(endpoints)
                .flatMap(new Func1<Endpoint, Observable<LifecycleState>>() {
                    @Override
                    public Observable<LifecycleState> call(Endpoint endpoint) {
                        LOGGER.debug(logIdent(hostname, AbstractDynamicService.this)
                                + "Initializing disconnect on Endpoint.");
                        return endpoint.disconnect();
                    }
                })
                .lastOrDefault(LifecycleState.DISCONNECTED)
                .map(new Func1<LifecycleState, LifecycleState>() {
                    @Override
                    public LifecycleState call(final LifecycleState state) {
                        endpointStates.terminate();
                        return state();
                    }
                });
    }

    @Override
    public BucketServiceMapping mapping() {
        return type().mapping();
    }

    protected Endpoint createEndpoint() {
        return endpointFactory.create(hostname, bucket, password, port, env, responseBuffer);
    }

    protected static String logIdent(final String hostname, final Service service) {
        return "[" + hostname + "][" + service.getClass().getSimpleName() + "]: ";
    }

    protected Endpoint[] endpoints() {
        return endpoints;
    }

    protected EndpointStateZipper endpointStates() {
        return endpointStates;
    }
}
