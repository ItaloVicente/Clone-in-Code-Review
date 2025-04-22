        Endpoint endpoint = mock(Endpoint.class);
        when(endpoint.states()).thenReturn(Observable.from(LifecycleState.DISCONNECTED));
        List<Endpoint> endpoints = Arrays.asList(endpoint);
        Service.EndpointFactory factory = new DummyService.DummyEndpointFactory(endpoints.iterator());

        Service service = new DummyService(hostname, bucket, password, port, environment, 1,
            mock(SelectionStrategy.class), factory);
