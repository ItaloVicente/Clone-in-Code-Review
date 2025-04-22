
    @Test
    public void shouldSerializeEjectionMethod() {
        BucketSettings settings = DefaultBucketSettings.builder()
                .ejectionMethod(EjectionMethod.FULL)
                .build();

        DefaultAsyncClusterManager clusterManager = new DefaultAsyncClusterManager("login", "password", null, null, null);
        String payload = clusterManager.getConfigureBucketPayload(settings, false);

        System.err.println(payload);

        assertTrue(payload.contains("evictionPolicy=fullEviction"));
    }
