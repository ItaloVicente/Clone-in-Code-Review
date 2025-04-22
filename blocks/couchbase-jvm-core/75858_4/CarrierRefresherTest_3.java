    @Test
    public void shouldFreeResourcesAtShutdown() {
        ClusterFacade cluster = mock(ClusterFacade.class);
        ConfigurationProvider provider = mock(ConfigurationProvider.class);
        CarrierRefresher refresher = new CarrierRefresher(ENVIRONMENT, cluster);
        refresher.provider(provider);

        assertFalse(refresher.pollerSubscription().isUnsubscribed());
        refresher.shutdown().toBlocking().single();
        assertTrue(refresher.pollerSubscription().isUnsubscribed());
    }
