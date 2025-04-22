    private static final TimeUnit TIMEOUT_UNIT = TimeUnit.MILLISECONDS;
    private AsyncViewResult asyncViewResult;
    private final long timeout;
    private final CouchbaseEnvironment env;
    private final Bucket bucket;

    public DefaultViewResult(CouchbaseEnvironment env, Bucket bucket, Observable<AsyncViewRow>rows, int totalRows, boolean success, JsonObject error, JsonObject debug) {
        asyncViewResult = new DefaultAsyncViewResult(rows, totalRows, success, error, debug);
        this.timeout = env.viewTimeout();
        this.env = env;
        this.bucket = bucket;
    }

    @Override
    public List<ViewRow> allRows() {
        return allRows(timeout, TIMEOUT_UNIT);
    }

    @Override
    public List<ViewRow> allRows(long timeout, TimeUnit timeUnit) {
        return asyncViewResult
            .rows()
            .map(new Func1<AsyncViewRow, ViewRow>() {
                @Override
                public ViewRow call(AsyncViewRow asyncViewRow) {
                    return new DefaultViewRow(env, bucket, asyncViewRow.id(), asyncViewRow.key(), asyncViewRow.value());
                }
            })
            .toList()
            .timeout(timeout, timeUnit)
            .toBlocking()
            .single();
    }
