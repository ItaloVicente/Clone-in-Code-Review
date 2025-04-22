    private final ExecutorService executorService = new ExecutorServiceProducer().produceUnmanagedExecutorService();

    @Test
    public void testShutdownByStop() throws Exception {
        ExecutorService executor = Executors.newCachedThreadPool();
        Daemon d = new Daemon(null,
                              executor,
                              executorService);
        d.start();
        assertTrue(d.isRunning());

        d.stop();

        assertFalse(d.isRunning());
    }

    @Test
    public void testShutdownByThreadPoolTermination() throws Exception {
        ExecutorService executor = Executors.newCachedThreadPool();
        Daemon d = new Daemon(null,
                              executor,
                              executorService);
        d.start();
        assertTrue(d.isRunning());

        executor.shutdownNow();
        executor.awaitTermination(10,
                                  TimeUnit.SECONDS);

        assertFalse(d.isRunning());
    }
