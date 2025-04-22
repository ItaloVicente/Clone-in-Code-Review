    @Test
    public void shouldDisconnectEndpointsWhenServiceDisconnects() throws Exception {
        InstrumentedService service = new InstrumentedService(host, bucket, password, port, ctx, factory);

        Endpoint endpoint = mock(Endpoint.class);
        final EndpointStates endpointStates = new EndpointStates(LifecycleState.CONNECTING);
        when(endpoint.states()).thenReturn(endpointStates.states());
        when(endpoint.connect()).thenReturn(Observable.just(LifecycleState.CONNECTED));
        when(factory.create(host, bucket, bucket, password, port, ctx)).thenReturn(endpoint);

        assertEquals(0, service.endpoints().size());
        assertEquals(LifecycleState.IDLE, service.connect().toBlocking().single());
        assertEquals(0, service.endpoints().size());

        CouchbaseRequest req = mock(CouchbaseRequest.class);
        AsyncSubject<CouchbaseResponse> reqObservable = AsyncSubject.create();
        when(req.observable()).thenReturn(reqObservable);
        service.send(req);

        endpointStates.transitionState(LifecycleState.CONNECTED);

        verify(endpoint, times(1)).send(req);
        verify(endpoint, times(1)).send(SignalFlush.INSTANCE);

        service.disconnect().toBlocking().single();

        verify(endpoint, times(1)).disconnect();
    }

