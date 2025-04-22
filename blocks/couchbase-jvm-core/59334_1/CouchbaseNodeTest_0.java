
    @Test
    public void shouldCacheEnabledServices() {
        ServiceRegistry registryMock = mock(ServiceRegistry.class);

        CouchbaseNode node = new CouchbaseNode(host, registryMock, environment, null);

        assertFalse(node.serviceEnabled(ServiceType.BINARY));
        assertFalse(node.serviceEnabled(ServiceType.CONFIG));
        assertFalse(node.serviceEnabled(ServiceType.QUERY));

        node.addService(new AddServiceRequest(ServiceType.BINARY, "bucket", null, 0, host))
                .toBlocking().single();

        assertTrue(node.serviceEnabled(ServiceType.BINARY));
        assertFalse(node.serviceEnabled(ServiceType.CONFIG));
        assertFalse(node.serviceEnabled(ServiceType.QUERY));

        node.addService(new AddServiceRequest(ServiceType.CONFIG, null, null, 0, host))
                .toBlocking().single();

        assertTrue(node.serviceEnabled(ServiceType.BINARY));
        assertTrue(node.serviceEnabled(ServiceType.CONFIG));
        assertFalse(node.serviceEnabled(ServiceType.QUERY));

        Service binaryServiceMock = mock(Service.class);
        when(binaryServiceMock.type()).thenReturn(ServiceType.BINARY);
        when(registryMock.serviceBy(ServiceType.BINARY, "bucket")).thenReturn(binaryServiceMock);
        Service configServiceMock = mock(Service.class);
        when(configServiceMock.type()).thenReturn(ServiceType.CONFIG);
        when(registryMock.serviceBy(ServiceType.CONFIG, null)).thenReturn(configServiceMock);

        node.removeService(new RemoveServiceRequest(ServiceType.BINARY, "bucket", host))
                .toBlocking().single();

        assertFalse(node.serviceEnabled(ServiceType.BINARY));
        assertTrue(node.serviceEnabled(ServiceType.CONFIG));
        assertFalse(node.serviceEnabled(ServiceType.QUERY));


        node.removeService(new RemoveServiceRequest(ServiceType.CONFIG, null, host))
                .toBlocking().single();

        assertFalse(node.serviceEnabled(ServiceType.BINARY));
        assertFalse(node.serviceEnabled(ServiceType.CONFIG));
        assertFalse(node.serviceEnabled(ServiceType.QUERY));

    }
