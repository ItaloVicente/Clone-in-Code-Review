    public static final String INDEX_WATCH_LOG_NAME = "indexWatch";

    private static final CouchbaseLogger INDEX_WATCH_LOG = CouchbaseLoggerFactory.getInstance(INDEX_WATCH_LOG_NAME);
    private static final int INDEX_WATCH_MAX_ATTEMPTS = 5;
    private static final Delay INDEX_WATCH_DELAY = Delay.linear(TimeUnit.MILLISECONDS, 1000, 50, 500);

