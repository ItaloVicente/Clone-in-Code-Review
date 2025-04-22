    @Test
    public void shouldHandleEmptyNodeListWithoutCrashing() {
        Locator locator = new SearchLocator(0);
        locator.locateAndDispatch(
            mock(SearchQueryRequest.class),
            Collections.<Node>emptyList(),
            mock(ClusterConfig.class),
            null,
            null
        );
    }

