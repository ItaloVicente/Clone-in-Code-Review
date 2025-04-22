
    @Test
    public void shouldSerializeCompressionMode() {
        BucketSettings settings = DefaultBucketSettings.builder()
            .compressionMode(CompressionMode.ACTIVE)
            .build();

        DefaultAsyncClusterManager clusterManager = new DefaultAsyncClusterManager("login", "password", null, null, null);
        String payload = clusterManager.getConfigureBucketPayload(settings, false);

        assertTrue(payload.contains("compressionMode=active"));
    }
