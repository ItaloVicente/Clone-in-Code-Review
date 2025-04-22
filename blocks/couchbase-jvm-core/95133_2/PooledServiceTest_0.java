    @Test
    public void shouldNotCreateNewEndpointOrAcceptRequestIfNodeIsDown() {
        EndpointFactoryMock ef = EndpointFactoryMock.simple(envCtx);
        ef.onCreate(new Action2<Endpoint, BehaviorSubject<LifecycleState>>() {
            @Override
            public void call(final Endpoint endpoint, final BehaviorSubject<LifecycleState> states) {
                when(endpoint.connect()).then(new Answer<Observable<LifecycleState>>() {
                    @Override
                    public Observable<LifecycleState> answer(InvocationOnMock invocation) throws Throwable {
                        states.onNext(LifecycleState.DISCONNECTING);
                        return Observable.error(new ConnectException("could not connect to remote"));
                    }
                });
            }
        });

        SelectionStrategy ss = new RoundRobinSelectionStrategy();
        MockedService ms = new MockedService(ServiceType.QUERY, ef, ssc(0, 1), ss);
        ms.connect().toBlocking().single();

        Tuple2<CouchbaseRequest, TestSubscriber<CouchbaseResponse>> mr1 = mockRequest();

        ms.send(mr1.value1());
        ef.advanceAll(LifecycleState.CONNECTED);

        assertEquals(1, ef.endpointCount());

        mr1.value2().assertError(RequestCancelledException.class);

        Tuple2<CouchbaseRequest, TestSubscriber<CouchbaseResponse>> mr2 = mockRequest();
        ms.send(mr2.value1());

        assertEquals(1, ef.endpointCount());
        assertEquals(0, ef.endpointSendCalled());

        mr1.value2().assertError(RequestCancelledException.class);
    }

