    private static final TimeUnit TIMEOUT_UNIT = TimeUnit.MILLISECONDS;
    private final DefaultAsyncQueryResult asyncQueryResult;
    private final long timeout;

    public DefaultQueryResult(CouchbaseEnvironment environment, Observable<AsyncQueryRow> rows,
        Observable<JsonObject> info, JsonObject error, boolean success) {
        this.asyncQueryResult = new DefaultAsyncQueryResult(rows, info, error, success);
        this.timeout = environment.managementTimeout();
    }
