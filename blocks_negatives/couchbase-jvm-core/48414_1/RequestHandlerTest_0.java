        QueryRequest mockQueryRequest = mock(QueryRequest.class);
        DCPRequest mockDcpRequest = mock(DCPRequest.class);
        CouchbaseRequest mockKeyValueRequest = mock(GetRequest.class);
        CoreEnvironment env = DefaultCoreEnvironment
            .builder()
            .dcpEnabled(true)
            .build();
        RequestHandler handler = new DummyLocatorClusterNodeHandler(env);

        assertFeatureForRequest(handler, mockQueryRequest, false);
        assertFeatureForRequest(handler, mockDcpRequest, true);
        assertFeatureForRequest(handler, mockKeyValueRequest, true);
