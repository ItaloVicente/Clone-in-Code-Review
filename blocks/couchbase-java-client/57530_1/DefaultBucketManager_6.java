    @Override
    public List<IndexInfo> listIndexes() {
        return listIndexes(timeout, TIMEOUT_UNIT);
    }

    @Override
    public List<IndexInfo> listIndexes(long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucketManager.listIndexes().toList(), timeout, timeUnit);
    }

    @Override
    public boolean createPrimaryIndex(boolean ignoreIfExist, boolean defer) {
        return createPrimaryIndex(ignoreIfExist, defer, timeout, TIMEOUT_UNIT);
    }

    @Override
    public boolean createPrimaryIndex(boolean ignoreIfExist, boolean defer, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucketManager.createPrimaryIndex(ignoreIfExist, defer), timeout, timeUnit);
    }

    private boolean createIndex(String indexName, boolean ignoreIfExist, boolean defer, long timeout, TimeUnit timeUnit, Object... fields) {
        return Blocking.blockForSingle(asyncBucketManager.createIndex(indexName, ignoreIfExist, defer, fields),
                timeout, timeUnit);
    }

    @Override
    public boolean createIndex(String indexName, boolean ignoreIfExist, boolean defer, Object... fields) {
        return createIndex(indexName, ignoreIfExist, defer, timeout, TIMEOUT_UNIT, fields);
    }

    @Override
    public boolean createIndex(String indexName, List<Object> fields, boolean ignoreIfExist, boolean defer) {
        return createIndex(indexName, ignoreIfExist, defer, timeout, TIMEOUT_UNIT, fields.toArray());
    }

    @Override
    public boolean createIndex(String indexName, List<Object> fields, boolean ignoreIfExist, boolean defer,
            long timeout, TimeUnit timeUnit) {
        return createIndex(indexName, ignoreIfExist, defer, timeout, timeUnit, fields.toArray());
    }

    @Override
    public boolean dropPrimaryIndex(boolean ignoreIfNotExist) {
        return dropPrimaryIndex(ignoreIfNotExist, timeout, TIMEOUT_UNIT);
    }

    @Override
    public boolean dropPrimaryIndex(boolean ignoreIfNotExist, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucketManager.dropPrimaryIndex(ignoreIfNotExist), timeout, timeUnit);
    }

    @Override
    public boolean dropIndex(String name, boolean ignoreIfNotExist) {
        return dropIndex(name, ignoreIfNotExist, timeout, TIMEOUT_UNIT);
    }

    @Override
    public boolean dropIndex(String name, boolean ignoreIfNotExist, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucketManager.dropIndex(name, ignoreIfNotExist), timeout, timeUnit);
    }

    @Override
    public List<String> buildDeferredIndexes() {
        return buildDeferredIndexes(timeout, TIMEOUT_UNIT);
    }

    @Override
    public List<String> buildDeferredIndexes(long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucketManager.buildDeferredIndexes(), timeout, timeUnit);
    }

    @Override
    public List<IndexInfo> watchIndexes(boolean watchPrimary, long watchTimeout, TimeUnit watchTimeUnit, String... watchList) {
        return asyncBucketManager.watchIndexes(watchPrimary, watchTimeout, watchTimeUnit, watchList)
                .toList()
                .toBlocking()
                .single();
    }

    @Override
    public List<IndexInfo> watchIndexes(boolean watchPrimary, long watchTimeout, TimeUnit watchTimeUnit,
            List<String> watchList) {
        return asyncBucketManager.watchIndexes(watchPrimary, watchTimeout, watchTimeUnit, watchList)
                .toList()
                .toBlocking()
                .single();
    }

    @Override
    public boolean watchIndex(String indexName, long watchTimeout, TimeUnit watchTimeUnit) {
        Boolean isOffline = asyncBucketManager.watchIndex(indexName, watchTimeout, watchTimeUnit)
                .isEmpty()
                .toBlocking()
                .single();

        return !isOffline;
    }
