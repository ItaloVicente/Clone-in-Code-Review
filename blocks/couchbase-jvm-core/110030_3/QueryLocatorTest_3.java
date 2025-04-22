    @Test
    public void shouldHandleEmptyNodeListWithoutCrashing() {
        Locator locator = new QueryLocator(0);
        locator.locateAndDispatch(
            mock(GenericQueryRequest.class),
            Collections.<Node>emptyList(),
            mock(ClusterConfig.class),
            null,
            null
        );
    }

