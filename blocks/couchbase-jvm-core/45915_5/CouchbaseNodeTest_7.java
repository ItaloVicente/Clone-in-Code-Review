
    @Test(expected = RequestCancelledException.class)
    public void shouldCancelIfServiceCouldNotBeLocated() {
        ServiceRegistry registryMock = mock(ServiceRegistry.class);
        Service serviceMock = mock(Service.class);
        when(registryMock.serviceBy(ServiceType.BINARY, "bucket")).thenReturn(serviceMock);
        when(serviceMock.states()).thenReturn(Observable.<LifecycleState>empty());
        CoreEnvironment env = mock(CoreEnvironment.class);
        when(env.retryStrategy()).thenReturn(FailFastRetryStrategy.INSTANCE);

        CouchbaseNode node = new CouchbaseNode(host, registryMock, env, null);

        CouchbaseRequest request = mock(CouchbaseRequest.class);
        AsyncSubject<CouchbaseResponse> response = AsyncSubject.create();
        when(request.observable()).thenReturn(response);
        node.send(request);

        response.toBlocking().single();
    }
