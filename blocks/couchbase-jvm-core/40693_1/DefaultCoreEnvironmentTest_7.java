    public void sysPropertyShouldTakePrecedence() throws Exception {

        System.setProperty("com.couchbase.binaryServiceEndpoints", "10");

        CoreEnvironment env = DefaultCoreEnvironment
            .builder()
            .binaryServiceEndpoints(3)
            .build();
        assertNotNull(env.ioPool());
        assertNotNull(env.scheduler());

        assertEquals(10, env.binaryServiceEndpoints());
        assertTrue(env.shutdown().toBlocking().single());

        System.clearProperty("com.couchbase.binaryServiceEndpoints");
