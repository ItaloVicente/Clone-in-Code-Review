    final AtomicReference<FixedSchedulerPool> pool;
    private final int poolSize;

    static final FixedSchedulerPool NONE = new FixedSchedulerPool(0);

    static final PoolWorker SHUTDOWN_WORKER;
    static {
        SHUTDOWN_WORKER = new PoolWorker(new RxThreadFactory("cb-computationShutdown-"));
        SHUTDOWN_WORKER.unsubscribe();
    }

