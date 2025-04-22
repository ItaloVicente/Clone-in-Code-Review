    @Test
    public void shouldForceTimeoutOfSocketConnectDoesNotReturn() {
        BootstrapAdapter bootstrap = mock(BootstrapAdapter.class);
        when(bootstrap.connect()).thenReturn(channel.newPromise()); // this promise never completes
        Endpoint endpoint = new DummyEndpoint(bootstrap, environment);

        Observable<LifecycleState> observable = endpoint.connect();

        TestSubscriber<LifecycleState> testSubscriber = new TestSubscriber<LifecycleState>();
        observable.subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();

        List<Throwable> errors = testSubscriber.getOnErrorEvents();
        assertEquals(1, errors.size());
        assertEquals(ConnectTimeoutException.class, errors.get(0).getClass());
    }

