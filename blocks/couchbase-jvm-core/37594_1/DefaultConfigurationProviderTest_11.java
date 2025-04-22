        Observable<ClusterConfig> configObservable = provider.openBucket("bucket", "password");
        try {
            configObservable.toBlockingObservable().single();
            assertTrue(false);
        } catch(ConfigurationException ex) {
            assertEquals("Could not open bucket.", ex.getMessage());
        } catch(Exception ex) {
            assertTrue(false);
        }
