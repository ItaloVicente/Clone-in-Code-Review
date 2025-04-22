
    @Test
    public void shouldHandleEmptyNodeListWithoutCrashing() {
        Locator locator = new ConfigLocator(0);
        locator.locateAndDispatch(
            mock(GetDesignDocumentsRequest.class),
            Collections.<Node>emptyList(),
            mock(ClusterConfig.class),
            null,
            null
        );
    }
