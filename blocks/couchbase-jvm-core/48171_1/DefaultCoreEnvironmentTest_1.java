    @Test
    public void shouldApplyMinPoolSize() throws Exception {
        CoreEnvironment env = DefaultCoreEnvironment
                .builder()
                .ioPoolSize(1)
                .computationPoolSize(1)
                .build();

        assertEquals(DefaultCoreEnvironment.MIN_POOL_SIZE, env.ioPoolSize());
        assertEquals(DefaultCoreEnvironment.MIN_POOL_SIZE, env.computationPoolSize());
    }

