    @Test(expected = RequestCancelledException.class)
    public void shouldCancelRequestOnFailFastStrategy() {
        Endpoint endpoint1 = mock(Endpoint.class);
        EndpointStates e1s = new EndpointStates(LifecycleState.DISCONNECTED);
        when(endpoint1.states()).thenReturn(e1s.states());
        when(endpoint1.connect()).thenReturn(Observable.just(LifecycleState.DISCONNECTED));
        when(endpoint1.disconnect()).thenReturn(Observable.just(LifecycleState.DISCONNECTING));
        CoreEnvironment env = mock(CoreEnvironment.class);
        when(env.retryStrategy()).thenReturn(FailFastRetryStrategy.INSTANCE);

        int endpoints = 1;
        SelectionStrategy strategy = mock(SelectionStrategy.class);
        when(strategy.select(any(CouchbaseRequest.class), any(Endpoint[].class))).thenReturn(null);
        InstrumentedService service = new InstrumentedService(host, bucket, password, port, env, endpoints,
                endpoints, strategy, null, factory);

        CouchbaseRequest request = mock(CouchbaseRequest.class);
        AsyncSubject<CouchbaseResponse> response = AsyncSubject.create();
        when(request.observable()).thenReturn(response);
        service.send(request);

        response.toBlocking().single();
    }

