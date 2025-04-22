        Service configServiceMock = mock(Service.class);
        when(configServiceMock.type()).thenReturn(ServiceType.CONFIG);
        when(configServiceMock.states()).thenReturn(Observable.just(LifecycleState.CONNECTED));
        when(configServiceMock.connect()).thenReturn(Observable.just(LifecycleState.CONNECTED));
        when(serviceFactory.create(anyString(), anyString(), anyString(), anyString(),
                eq(0), same(environment), eq(ServiceType.CONFIG), any(RingBuffer.class))).thenReturn(configServiceMock);

        CouchbaseNode node = new CouchbaseNode(host, registryMock, environment, null, serviceFactory);
