package com.couchbase.client.core.service;

import com.couchbase.client.core.ResponseEvent;
import com.couchbase.client.core.env.CoreEnvironment;
import com.couchbase.client.core.state.AbstractStateMachine;
import com.couchbase.client.core.state.LifecycleState;
import com.lmax.disruptor.RingBuffer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

public class AbstractLazyServiceTest {

    private final String host = "hostname";
    private final String bucket = "bucket";
    private final String password = "";
    private final int port = 1234;

    private CoreEnvironment env;
    private Service.EndpointFactory factory;

    @Before
    public void setup() {
        env = mock(CoreEnvironment.class);
        factory = mock(Service.EndpointFactory.class);
    }

    @Test
    public void shouldNotHaveServiceOnStart() {
        InstrumentedService service = new InstrumentedService(host, bucket, password, port, env, null, factory);
        assertEquals(LifecycleState.IDLE, service.state());
        assertNull(service.endpoint());
    }

    @Test
    public void shouldLazilyCreateAndReuseEndpoint() {

    }

    @Test
    public void shouldClearEndpointWhenDisconnected() {

    }

    class InstrumentedService extends AbstractLazyService {

        public InstrumentedService(String hostname, String bucket, String password, int port,
            CoreEnvironment env, RingBuffer<ResponseEvent> responseBuffer, EndpointFactory endpointFactory) {
            super(hostname, bucket, password, port, env, responseBuffer, endpointFactory);
        }

        @Override
        public ServiceType type() {
            return ServiceType.DCP;
        }

    }

    class EndpointStates extends AbstractStateMachine<LifecycleState> {

        public EndpointStates(LifecycleState initialState) {
            super(initialState);
        }

        @Override
        public void transitionState(LifecycleState newState) {
            super.transitionState(newState);
        }
    }
}
