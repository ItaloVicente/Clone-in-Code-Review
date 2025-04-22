
    @Override
    public boolean watchIndex(String indexName, long watchTimeout, TimeUnit watchTimeUnit) {
        Boolean isOffline = asyncBucketManager.watchIndex(indexName, watchTimeout, watchTimeUnit)
                .isEmpty()
                .toBlocking()
                .single();

        return !isOffline;
    }

