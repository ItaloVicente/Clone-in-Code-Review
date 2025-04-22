    @Test
    public void shouldIgnoreDisconnectIfDisconnecting() {
        EndpointFactoryMock ef = EndpointFactoryMock.simple(envCtx);
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
        MockedService ms = new MockedService(ServiceType.BINARY, ef, ssc(4, 4), null);
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

