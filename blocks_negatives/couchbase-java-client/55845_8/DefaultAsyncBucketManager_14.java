    /**
     * A transformer (to be used with {@link Observable#compose(Observable.Transformer)}) that puts a timeout on an
     * {@link IndexInfo} Observable and will consider {@link IndexesNotReadyException} inside a
     * {@link CannotRetryException} and {@link TimeoutException} to be non-failing cases (resulting in an empty
     * observable), whereas all other errors are propagated as is.
     *
     * @param watchTimeout the timeout to set.
     * @param watchTimeUnit the time unit for the timeout.
     * @param indexName null for a global timeout, or the name of the single index being watched.
     */
    private static Observable.Transformer<IndexInfo, IndexInfo> safeAbort(final long watchTimeout, final TimeUnit watchTimeUnit,
            final String indexName) {
        return new Observable.Transformer<IndexInfo, IndexInfo>() {
