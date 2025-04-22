    @Test
    public void shouldNullServicePoolsIfNotOverridden() {
        CoreEnvironment env = DefaultCoreEnvironment.create();
        assertNull(env.kvIoPool());
        assertNull(env.viewIoPool());
        assertNull(env.searchIoPool());
        assertNull(env.queryIoPool());
        env.shutdown();
    }

    @Test
    public void shouldOverrideAndShutdownServicePools() {
        EventLoopGroup elg = new NioEventLoopGroup();
        CoreEnvironment env = DefaultCoreEnvironment.builder()
            .kvIoPool(elg, new NoOpShutdownHook())
            .viewIoPool(elg, new NoOpShutdownHook())
            .searchIoPool(elg, new NoOpShutdownHook())
            .queryIoPool(elg, new NoOpShutdownHook())
            .build();

        env.shutdown();
        assertFalse(elg.isShutdown());
        elg.shutdownGracefully().awaitUninterruptibly(3000);
        assertTrue(elg.isShutdown());
    }

