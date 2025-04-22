            if (queryWasEnabled != null) {
                System.setProperty("com.couchbase.queryEnabled", queryWasEnabled);
            }
        }
    }

    @Test
    public void shouldAllowQueryWhenQueryFeatureEnabled() {
        String dcpWasEnabled = System.getProperty("com.couchbase.dcpEnabled");
        String queryWasEnabled = System.getProperty("com.couchbase.queryEnabled");
        try {
            System.getProperties().remove("com.couchbase.dcpEnabled");
            System.getProperties().remove("com.couchbase.queryEnabled");
            QueryRequest mockQueryRequest = mock(QueryRequest.class);
            DCPRequest mockDcpRequest = mock(DCPRequest.class);
            CouchbaseRequest mockKeyValueRequest = mock(GetRequest.class);
            CoreEnvironment env = DefaultCoreEnvironment.builder()
                    .queryEnabled(true)
                    .dcpEnabled(true)
                    .build();
            RequestHandler handler = new DummyLocatorClusterNodeHandler(env);

            assertFeatureForRequest(handler, mockQueryRequest, true);
            assertFeatureForRequest(handler, mockDcpRequest, true);
            assertFeatureForRequest(handler, mockKeyValueRequest, true);
        } finally {
            if (dcpWasEnabled != null) {
                System.setProperty("com.couchbase.dcpEnabled", dcpWasEnabled);
            }
            if (queryWasEnabled != null) {
                System.setProperty("com.couchbase.queryEnabled", queryWasEnabled);
            }
