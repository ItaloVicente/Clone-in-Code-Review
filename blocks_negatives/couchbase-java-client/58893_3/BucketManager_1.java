
    /**
     * Watches a specific index, polling the query service until the index becomes "online" or the watchTimeout has expired.
     *
     * Note: You can activate DEBUG level logs on the "{@value DefaultAsyncBucketManager#INDEX_WATCH_LOG_NAME}" logger
     * to see various stages of the polling.
     *
     * @param indexName the name of the index to watch. For primary indexes, use {@link Index#PRIMARY_NAME}.
     * @param watchTimeout the maximum duration for which to poll for the index to become online.
     * @param watchTimeUnit the time unit for the watchTimeout.
     * @return true if the index could be observed to become online within the watchTimeout, false otherwise.
     */
    @InterfaceStability.Experimental
    boolean watchIndex(String indexName, long watchTimeout, TimeUnit watchTimeUnit);
