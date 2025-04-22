    public Daemon(final InetSocketAddress addr,
                  final Executor acceptThreadPool,
                  final ExecutorService executorService) {
        this(addr,
             acceptThreadPool,
             executorService,
             null);
    }
