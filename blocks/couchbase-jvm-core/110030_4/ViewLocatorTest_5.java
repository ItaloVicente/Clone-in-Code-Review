    @Test
    public void shouldHandleEmptyNodeListWithoutCrashing() {
        ViewQueryRequest request = mock(ViewQueryRequest.class);
        when(request.bucket()).thenReturn("default");

        ClusterConfig clusterConfig =  mock(ClusterConfig.class);
        when(clusterConfig.bucketConfig("default")).thenReturn(mock(CouchbaseBucketConfig.class));

        Locator locator = new ViewLocator(0);
        locator.locateAndDispatch(
            request,
            Collections.<Node>emptyList(),
            clusterConfig,
            null,
            null
        );
    }

