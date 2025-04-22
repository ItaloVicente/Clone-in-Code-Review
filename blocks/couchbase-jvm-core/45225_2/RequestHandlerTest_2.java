    private void assertFeatureForRequest(RequestHandler handler, CouchbaseRequest request, boolean expectedOk) {
        try {
            handler.checkFeaturesForRequest(request);
            if (!expectedOk) {
                fail();
            }
        } catch (UnsupportedOperationException e) {
            if (expectedOk) {
                fail();
            }
            assertTrue(e.getMessage().startsWith("Request type needs a feature to be enabled in environment"));
        }
    }

    @Test
    public void shouldPreventFeatureDependentRequestsWhenFeatureDisabled() {
        QueryRequest mockQueryRequest = mock(QueryRequest.class);
        DCPRequest mockDcpRequest = mock(DCPRequest.class);
        CouchbaseRequest mockKeyValueRequest = mock(GetRequest.class);
        CoreEnvironment env = DefaultCoreEnvironment.create();
        RequestHandler handler = new DummyLocatorClusterNodeHandler(env);

        assertFeatureForRequest(handler, mockQueryRequest, false);
        assertFeatureForRequest(handler, mockDcpRequest, false);
        assertFeatureForRequest(handler, mockKeyValueRequest, true);
    }

    @Test
    public void shouldAllowQueryWhenQueryFeatureEnabled() {
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
    }

    @Test
    public void shouldAllowDcpWhenDcpFeatureEnabled() {
        QueryRequest mockQueryRequest = mock(QueryRequest.class);
        DCPRequest mockDcpRequest = mock(DCPRequest.class);
        CouchbaseRequest mockKeyValueRequest = mock(GetRequest.class);
        CoreEnvironment env = DefaultCoreEnvironment.builder()
                                                    .dcpEnabled(true)
                                                    .build();
        RequestHandler handler = new DummyLocatorClusterNodeHandler(env);

        assertFeatureForRequest(handler, mockQueryRequest, false);
        assertFeatureForRequest(handler, mockDcpRequest, true);
        assertFeatureForRequest(handler, mockKeyValueRequest, true);
    }

