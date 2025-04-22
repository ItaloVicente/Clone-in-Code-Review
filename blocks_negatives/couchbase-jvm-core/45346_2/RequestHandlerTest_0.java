        QueryRequest mockQueryRequest = mock(QueryRequest.class);
        DCPRequest mockDcpRequest = mock(DCPRequest.class);
        CouchbaseRequest mockKeyValueRequest = mock(GetRequest.class);
        CoreEnvironment env = DefaultCoreEnvironment.create();
        RequestHandler handler = new DummyLocatorClusterNodeHandler(env);

        assertFeatureForRequest(handler, mockQueryRequest, false);
        assertFeatureForRequest(handler, mockDcpRequest, false);
        assertFeatureForRequest(handler, mockKeyValueRequest, true);
