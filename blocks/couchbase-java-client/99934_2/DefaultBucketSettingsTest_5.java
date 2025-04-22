    @Test
    public void shouldAllowToOverrideCompressionMode() {
        for (CompressionMode mode : CompressionMode.values()) {
            assertEquals(mode, DefaultBucketSettings.builder().compressionMode(mode).build().compressionMode());
        }
    }

