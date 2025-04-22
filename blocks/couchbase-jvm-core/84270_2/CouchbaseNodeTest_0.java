
    @Test
    public void shouldSetDispatchedHostnameAfterSend() {
        ServiceRegistry registryMock = mock(ServiceRegistry.class);
        ServiceFactory serviceFactory = mock(ServiceFactory.class);

        Service binaryServiceMock = mock(Service.class);
        when(binaryServiceMock.type()).thenReturn(ServiceType.BINARY);
        when(binaryServiceMock.states()).thenReturn(Observable.just(LifecycleState.CONNECTED));
        when(binaryServiceMock.connect()).thenReturn(Observable.just(LifecycleState.CONNECTED));
        when(serviceFactory.create(anyString(), anyString(), anyString(), anyString(),
                eq(0), same(environment), eq(ServiceType.BINARY), any(RingBuffer.class))).thenReturn(binaryServiceMock);


        CouchbaseNode node = new CouchbaseNode(host, registryMock, environment, null, serviceFactory);
       node.addService(new AddServiceRequest(ServiceType.BINARY, "bucket", null, 0, host))
                .toBlocking().single();

        CouchbaseRequest request = mock(CouchbaseRequest.class);
        node.send(request);

        verify(request, times(1)).dispatchHostname(any(String.class));

    }
