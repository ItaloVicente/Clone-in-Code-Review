
    @Test
    public void shouldThrowIfDisabledThroughConfiguration() {
        Environment environment = mock(Environment.class);
        when(environment.bootstrapCarrierEnabled()).thenReturn(false);
        Cluster cluster = mock(Cluster.class);

        CarrierLoader loader = new CarrierLoader(cluster, environment);
        try {
            loader.discoverConfig("bucket", "password", "hostname").toBlockingObservable().single();
            assertTrue(false);
        } catch(ConfigurationException ex) {
            assertEquals("Carrier Bootstrap disabled through configuration.", ex.getMessage());
        } catch(Exception ex) {
            assertTrue(false);
        }
    }
