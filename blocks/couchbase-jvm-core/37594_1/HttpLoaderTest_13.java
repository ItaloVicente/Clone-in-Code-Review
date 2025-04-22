    @Test
    public void shouldThrowIfDisabledThroughConfiguration() {
        Environment environment = mock(Environment.class);
        when(environment.bootstrapHttpEnabled()).thenReturn(false);
        Cluster cluster = mock(Cluster.class);

        HttpLoader loader = new HttpLoader(cluster, environment);
        try {
            loader.discoverConfig("bucket", "password", "hostname").toBlockingObservable().single();
            assertTrue(false);
        } catch(ConfigurationException ex) {
            assertEquals("Http Bootstrap disabled through configuration.", ex.getMessage());
        } catch(Exception ex) {
            assertTrue(false);
        }
    }

