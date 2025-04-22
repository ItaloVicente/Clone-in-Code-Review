    @Test(expected = RequestCancelledException.class)
    public void shouldCancelOnRetryPolicyFailFast() throws Exception {
        CoreEnvironment env = mock(CoreEnvironment.class);
        when(env.retryStrategy()).thenReturn(FailFastRetryStrategy.INSTANCE);
        ClusterConfig mockClusterConfig = mock(ClusterConfig.class);
        when(mockClusterConfig.hasBucket(anyString())).thenReturn(Boolean.TRUE);
        Observable<ClusterConfig> mockConfigObservable = Observable.just(mockClusterConfig);

        RequestHandler handler = new DummyLocatorClusterNodeHandler(env, mockConfigObservable);
        Node mockNode = mock(Node.class);
        when(mockNode.connect()).thenReturn(Observable.just(LifecycleState.DISCONNECTED));
        when(mockNode.state()).thenReturn(LifecycleState.DISCONNECTED);
        handler.addNode(mockNode).toBlocking().single();

        RequestEvent mockEvent = mock(RequestEvent.class);
        CouchbaseRequest mockRequest = mock(CouchbaseRequest.class);
        AsyncSubject<CouchbaseResponse> response = AsyncSubject.create();
        when(mockEvent.getRequest()).thenReturn(mockRequest);
        when(mockRequest.observable()).thenReturn(response);
        handler.onEvent(mockEvent, 0, true);

        verify(mockNode, times(1)).send(SignalFlush.INSTANCE);
        verify(mockNode, never()).send(mockRequest);
        verify(mockEvent).setRequest(null);

        response.toBlocking().single();
    }

