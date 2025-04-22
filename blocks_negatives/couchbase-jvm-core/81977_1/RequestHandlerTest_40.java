    @Test
    public void shouldPreventFeatureDependentRequestsWhenFeatureDisabled() {
        String dcpWasEnabled = System.getProperty("com.couchbase.dcpEnabled");
        try {
            System.getProperties().remove("com.couchbase.dcpEnabled");
            DCPRequest mockDcpRequest = mock(DCPRequest.class);
            CouchbaseRequest mockKeyValueRequest = mock(GetRequest.class);
            CoreEnvironment env = DefaultCoreEnvironment.builder()
                    .dcpEnabled(false)
                    .build();
            RequestHandler handler = new DummyLocatorClusterNodeHandler(env);

            assertFeatureForRequest(handler, mockDcpRequest, false);
            assertFeatureForRequest(handler, mockKeyValueRequest, true);
        } finally {
            if (dcpWasEnabled != null) {
                System.setProperty("com.couchbase.dcpEnabled", dcpWasEnabled);
            }
        }
    }

    @Test
    public void shouldAllowDcpWhenDcpFeatureEnabled() {
        String dcpWasEnabled = System.getProperty("com.couchbase.dcpEnabled");
        try {
            System.getProperties().remove("com.couchbase.dcpEnabled");
            DCPRequest mockDcpRequest = mock(DCPRequest.class);
            CouchbaseRequest mockKeyValueRequest = mock(GetRequest.class);
            CoreEnvironment env = DefaultCoreEnvironment
                    .builder()
                    .dcpEnabled(true)
                    .build();
            RequestHandler handler = new DummyLocatorClusterNodeHandler(env);

            assertFeatureForRequest(handler, mockDcpRequest, true);
            assertFeatureForRequest(handler, mockKeyValueRequest, true);
        } finally {
            if (dcpWasEnabled != null) {
                System.setProperty("com.couchbase.dcpEnabled", dcpWasEnabled);
            }
        }
    }

