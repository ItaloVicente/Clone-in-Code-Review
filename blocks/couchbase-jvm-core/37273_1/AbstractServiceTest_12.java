        Endpoint endpoint = mock(Endpoint.class);
        BehaviorSubject<LifecycleState> endpointStates = BehaviorSubject.create(LifecycleState.DISCONNECTED);
        when(endpoint.states()).thenReturn(endpointStates);
        when(endpoint.connect()).thenReturn(Observable.from(LifecycleState.CONNECTED));
        List<Endpoint> endpoints = Arrays.asList(endpoint);
        Service.EndpointFactory factory = new DummyService.DummyEndpointFactory(endpoints.iterator());
        Service service = new DummyService(hostname, bucket, password, port, environment, 1,
            mock(SelectionStrategy.class), factory);
