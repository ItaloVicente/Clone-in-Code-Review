    @Test
    public void shouldSetDispatchedHostnameAfterSend() {
        BootstrapAdapter bootstrap = mock(BootstrapAdapter.class);
        when(bootstrap.connect()).thenReturn(channel.newSucceededFuture());
        Endpoint endpoint = new DummyEndpoint(bootstrap, environment);

        Observable<LifecycleState> observable = endpoint.connect();
        assertEquals(LifecycleState.CONNECTED, observable.toBlocking().single());

        CouchbaseRequest mockRequest = mock(CouchbaseRequest.class);
        endpoint.send(mockRequest);
        channel.flush();

        assertEquals(1, channel.outboundMessages().size());
        assertTrue(channel.readOutbound() instanceof CouchbaseRequest);
        verify(mockRequest, times(1)).dispatchHostname("127.0.0.1");
    }

