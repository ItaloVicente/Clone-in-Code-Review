        assertEquals(DefaultCoreEnvironment.BINARY_ENDPOINTS, env.binaryServiceEndpoints());
        assertTrue(env.shutdown().toBlocking().single());
    }

    @Test
    public void shouldOverrideDefaults() throws Exception {
        CoreEnvironment env = DefaultCoreEnvironment
            .builder()
            .binaryServiceEndpoints(3)
            .build();
