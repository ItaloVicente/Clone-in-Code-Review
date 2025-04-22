    private final CoreEnvironment environment;

    private final AtomicLong nodeOffset = new AtomicLong(0);

    private final Map<String, Long> lastPollTimestamps = new ConcurrentHashMap<String, Long>();

    private final long pollFloorNs;

