    @Test
    public void shouldHandleEmptyNodeListWithoutCrashing() {
        Locator locator = new AnalyticsLocator(0);
        locator.locateAndDispatch(
            mock(GenericAnalyticsRequest.class),
            Collections.<Node>emptyList(),
            mock(ClusterConfig.class),
            null,
            null
        );
    }

