        ServiceFactory serviceFactory = mock(ServiceFactory.class);

        Service binaryServiceMock = mock(Service.class);
        when(binaryServiceMock.type()).thenReturn(ServiceType.BINARY);
        when(binaryServiceMock.states()).thenReturn(Observable.just(LifecycleState.CONNECTED));
        when(binaryServiceMock.connect()).thenReturn(Observable.just(LifecycleState.CONNECTED));
        when(serviceFactory.create(anyString(), anyString(), anyString(), anyString(),
        eq(0), same(environment), eq(ServiceType.BINARY), any(RingBuffer.class))).thenReturn(binaryServiceMock);
