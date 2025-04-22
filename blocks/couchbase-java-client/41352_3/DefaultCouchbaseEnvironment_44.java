    private static final String NAMESPACE = "com.couchbase.";

    private static final long MANAGEMENT_TIMEOUT = TimeUnit.SECONDS.toMillis(75);
    private static final long QUERY_TIMEOUT = TimeUnit.SECONDS.toMillis(75);
    private static final long VIEW_TIMEOUT = TimeUnit.SECONDS.toMillis(75);
    private static final long BINARY_TIMEOUT = 2500;
    private static final long CONNECT_TIMEOUT = TimeUnit.SECONDS.toMillis(5);
    private static final long DISCONNECT_TIMEOUT = TimeUnit.SECONDS.toMillis(5);

    private final long managementTimeout;
    private final long queryTimeout;
    private final long viewTimeout;
    private final long binaryTimeout;
    private final long connectTimeout;
    private final long disconnectTimeout;

