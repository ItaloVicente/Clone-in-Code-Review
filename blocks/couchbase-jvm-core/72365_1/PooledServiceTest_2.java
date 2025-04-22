package com.couchbase.client.core.service;

import com.couchbase.client.core.endpoint.Endpoint;
import com.couchbase.client.core.env.CoreEnvironment;
import com.couchbase.client.core.state.LifecycleState;
import org.junit.BeforeClass;
import org.junit.Test;
import rx.functions.Func1;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class PooledServiceTest {

    private static volatile CoreEnvironment ENV;

    @BeforeClass
    public static void setup() {
        ENV = mock(CoreEnvironment.class);
    }

    @Test(expected =  IllegalArgumentException.class)
    public void shouldFailIfMinIsNegative() {
        new MockedService(ServiceType.BINARY, EndpointFactoryMock.simple(null, null), -1, 2, true);
    }

    @Test(expected =  IllegalArgumentException.class)
    public void shouldFailIfMaxIsNegative() {
        new MockedService(ServiceType.BINARY, EndpointFactoryMock.simple(null, null), 0, -2, true);
    }

    @Test(expected =  IllegalArgumentException.class)
    public void shouldFailIfMaxIs0() {
        new MockedService(ServiceType.BINARY, EndpointFactoryMock.simple(null, null), 0, 0, true);
    }

    @Test(expected =  IllegalArgumentException.class)
    public void shouldFailIfMaxGreaterThanMinEndpoints() {
        new MockedService(ServiceType.BINARY, EndpointFactoryMock.simple(null, null), 3, 2, true);
    }

    @Test
    public void shouldBeIdleOnBootIfMinEndpointsIs0() {
        MockedService ms = new MockedService(ServiceType.CONFIG, EndpointFactoryMock.simple(null, null), 0, 10, false);

        assertEquals(ServiceType.CONFIG, ms.type());
        assertEquals(LifecycleState.IDLE, ms.state());

        LifecycleState afterConnectState = ms.connect().toBlocking().single();
        assertEquals(LifecycleState.IDLE, afterConnectState);
    }

    @Test
    public void shouldBeDisconnectedOnBootIfMinEndpointsIsGt0() {
        MockedService ms = new MockedService(ServiceType.BINARY, EndpointFactoryMock.simple(null, null), 3, 10, true);
        assertEquals(ServiceType.BINARY, ms.type());
        assertEquals(LifecycleState.DISCONNECTED, ms.state());
    }

    @Test
    public void shouldSuccessfullyBootstrapMinEndpoints() {
        EndpointFactoryMock ef = EndpointFactoryMock.simple(ENV, null);
        ef.onConnectTransition(new Func1<Endpoint, LifecycleState>() {
            @Override
            public LifecycleState call(Endpoint endpoint) {
                return LifecycleState.CONNECTING;
            }
        });
        MockedService ms = new MockedService(ServiceType.BINARY, ef, 3, 4, true);

        LifecycleState afterConnectState = ms.connect().toBlocking().single();
        assertEquals(LifecycleState.CONNECTING, afterConnectState);

        assertEquals(LifecycleState.CONNECTING, ms.state());
        ef.advance(0, LifecycleState.CONNECTED);
        assertEquals(LifecycleState.DEGRADED, ms.state());
        ef.advance(1, LifecycleState.CONNECTED);
        assertEquals(LifecycleState.DEGRADED, ms.state());
        ef.advance(2, LifecycleState.CONNECTED);
        assertEquals(LifecycleState.CONNECTED, ms.state());

        assertEquals(3, ef.endpointCount());
        assertEquals(3, ef.endpointConnectCalled());
    }

    @Test
    public void shouldIgnoreConnectIfConnecting() {
        EndpointFactoryMock ef = EndpointFactoryMock.simple(ENV, null);
        ef.onConnectTransition(new Func1<Endpoint, LifecycleState>() {
            @Override
            public LifecycleState call(Endpoint endpoint) {
                return LifecycleState.CONNECTING;
            }
        });
        MockedService ms = new MockedService(ServiceType.BINARY, ef, 1, 1, true);

        LifecycleState afterConnectState = ms.connect().toBlocking().single();
        assertEquals(LifecycleState.CONNECTING, afterConnectState);
        assertEquals(1, ef.endpointCount());

        afterConnectState = ms.connect().toBlocking().single();
        assertEquals(LifecycleState.CONNECTING, afterConnectState);
        assertEquals(1, ef.endpointCount());
        assertEquals(1, ef.endpointConnectCalled());
    }

    @Test
    public void shouldIgnoreConnectIfConnected() {
        EndpointFactoryMock ef = EndpointFactoryMock.simple(ENV, null);
        ef.onConnectTransition(new Func1<Endpoint, LifecycleState>() {
            @Override
            public LifecycleState call(Endpoint endpoint) {
                return LifecycleState.CONNECTING;
            }
        });
        MockedService ms = new MockedService(ServiceType.BINARY, ef, 1, 1, true);

        LifecycleState afterConnectState = ms.connect().toBlocking().single();
        assertEquals(LifecycleState.CONNECTING, afterConnectState);
        assertEquals(1, ef.endpointCount());

        ef.advance(0, LifecycleState.CONNECTED);

        afterConnectState = ms.connect().toBlocking().single();
        assertEquals(LifecycleState.CONNECTED, afterConnectState);
        assertEquals(1, ef.endpointCount());
        assertEquals(1, ef.endpointConnectCalled());
    }

    @Test
    public void shouldDisconnectIfInstructed() {
        EndpointFactoryMock ef = EndpointFactoryMock.simple(ENV, null);
        ef.onConnectTransition(new Func1<Endpoint, LifecycleState>() {
            @Override
            public LifecycleState call(Endpoint endpoint) {
                return LifecycleState.CONNECTING;
            }
        });
        ef.onDisconnectTransition(new Func1<Endpoint, LifecycleState>() {
            @Override
            public LifecycleState call(Endpoint endpoint) {
                return LifecycleState.DISCONNECTING;
            }
        });
        MockedService ms = new MockedService(ServiceType.BINARY, ef, 4, 4, true);
        LifecycleState afterConnectState = ms.connect().toBlocking().single();
        assertEquals(LifecycleState.CONNECTING, afterConnectState);
        assertEquals(4, ef.endpointCount());

        ef.advanceAll(LifecycleState.CONNECTED);

        LifecycleState stateAfterDisconnect = ms.disconnect().toBlocking().single();
        assertEquals(LifecycleState.DISCONNECTED, stateAfterDisconnect);
        assertEquals(4, ef.endpointDisconnectCalled());
    }

    @Test
    public void shouldIgnoreDisconnectIfDisconnecting() {
        EndpointFactoryMock ef = EndpointFactoryMock.simple(ENV, null);
        ef.onConnectTransition(new Func1<Endpoint, LifecycleState>() {
            @Override
            public LifecycleState call(Endpoint endpoint) {
                return LifecycleState.CONNECTING;
            }
        });
        ef.onDisconnectTransition(new Func1<Endpoint, LifecycleState>() {
            @Override
            public LifecycleState call(Endpoint endpoint) {
                return LifecycleState.DISCONNECTING;
            }
        });
        MockedService ms = new MockedService(ServiceType.BINARY, ef, 4, 4, true);
        LifecycleState afterConnectState = ms.connect().toBlocking().single();
        assertEquals(LifecycleState.CONNECTING, afterConnectState);
        assertEquals(4, ef.endpointCount());

        ef.advanceAll(LifecycleState.CONNECTED);

        ef.advance(0, LifecycleState.DISCONNECTING);
        ef.advance(1, LifecycleState.DISCONNECTING);
        ef.advance(2, LifecycleState.DISCONNECTING);
        ef.advance(3, LifecycleState.DISCONNECTING);

        assertEquals(LifecycleState.DISCONNECTING, ms.state());

        LifecycleState stateAfterDisconnect = ms.disconnect().toBlocking().single();
        assertEquals(LifecycleState.DISCONNECTING, stateAfterDisconnect);

        assertEquals(0, ef.endpointDisconnectCalled());

        stateAfterDisconnect = ms.disconnect().toBlocking().single();
        assertEquals(LifecycleState.DISCONNECTING, stateAfterDisconnect);
        assertEquals(0, ef.endpointDisconnectCalled());
    }

    @Test
    public void shouldIgnoreDisconnectIfDisconnected() {
        EndpointFactoryMock ef = EndpointFactoryMock.simple(null, null);
        MockedService ms = new MockedService(ServiceType.BINARY, ef, 1, 1, true);
        assertEquals(ServiceType.BINARY, ms.type());
        assertEquals(LifecycleState.DISCONNECTED, ms.state());

        LifecycleState afterDisconnectState = ms.disconnect().toBlocking().single();
        assertEquals(LifecycleState.DISCONNECTED, afterDisconnectState);
        assertEquals(0, ef.endpointDisconnectCalled());
    }

    @Test
    public void shouldGenerateIdentityLogLine() {
        MockedService ms = new MockedService(ServiceType.CONFIG, EndpointFactoryMock.simple(ENV, null), 0, 10, false);
        String actual = PooledService.logIdent("hostname", ms);
        assertEquals("[hostname][MockedService]: ", actual);

        actual = PooledService.logIdent(null, ms);
        assertEquals("[null][MockedService]: ", actual);
    }

    static class MockedService extends PooledService {

        private final ServiceType serviceType;

        MockedService(ServiceType st, EndpointFactoryMock ef, int minEndpoints, int maxEndpoints, boolean pipelining) {
            super(ef.getHostname(), ef.getBucket(), ef.getPassword(), ef.getPort(), ef.getEnv(),
                minEndpoints, maxEndpoints, ef.getResponseBuffer(), ef, pipelining);
            this.serviceType = st;
        }

        @Override
        public ServiceType type() {
            return serviceType;
        }
    }
}
