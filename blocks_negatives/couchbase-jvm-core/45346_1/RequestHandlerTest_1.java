        QueryRequest mockQueryRequest = mock(QueryRequest.class);
        DCPRequest mockDcpRequest = mock(DCPRequest.class);
        CouchbaseRequest mockKeyValueRequest = mock(GetRequest.class);
        CoreEnvironment env = DefaultCoreEnvironment.builder()
                .queryEnabled(true)
                .build();
        RequestHandler handler = new DummyLocatorClusterNodeHandler(env);

        assertFeatureForRequest(handler, mockQueryRequest, true);
        assertFeatureForRequest(handler, mockDcpRequest, false);
        assertFeatureForRequest(handler, mockKeyValueRequest, true);
