
        CoreEnvironment environment = mock(CoreEnvironment.class);
        AbstractEndpoint endpoint = mock(AbstractEndpoint.class);
        when(endpoint.environment()).thenReturn(environment);
        when(environment.userAgent()).thenReturn("Couchbase Client Mock");

        handler = new ConfigHandler(endpoint, responseBuffer.start(), queue);
