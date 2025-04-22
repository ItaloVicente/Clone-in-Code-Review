        Endpoint endpoint1 = mock(Endpoint.class);
        BehaviorSubject<LifecycleState> endpoint1States = BehaviorSubject.create(LifecycleState.DISCONNECTED);
        when(endpoint1.states()).thenReturn(endpoint1States);
        when(endpoint1.connect()).thenReturn(Observable.from(LifecycleState.CONNECTED));
        Endpoint endpoint2 = mock(Endpoint.class);
        BehaviorSubject<LifecycleState> endpoint2States = BehaviorSubject.create(LifecycleState.DISCONNECTED);
        when(endpoint2.states()).thenReturn(endpoint2States);
        when(endpoint2.connect()).thenReturn(Observable.from(LifecycleState.CONNECTED));
        Endpoint endpoint3 = mock(Endpoint.class);
        BehaviorSubject<LifecycleState> endpoint3States = BehaviorSubject.create(LifecycleState.DISCONNECTED);
        when(endpoint3.states()).thenReturn(endpoint3States);
        when(endpoint3.connect()).thenReturn(Observable.from(LifecycleState.CONNECTED));

        List<Endpoint> endpoints = Arrays.asList(endpoint1, endpoint2, endpoint3);
        Service.EndpointFactory factory = new DummyService.DummyEndpointFactory(endpoints.iterator());
        Service service = new DummyService(hostname, bucket, password, port, environment, 3,
            mock(SelectionStrategy.class), factory);
        service.connect().toBlockingObservable().single();
        endpoint1States.onNext(LifecycleState.CONNECTED);
        endpoint2States.onNext(LifecycleState.CONNECTING);
        endpoint3States.onNext(LifecycleState.CONNECTING);
        assertEquals(LifecycleState.DEGRADED, service.state());
