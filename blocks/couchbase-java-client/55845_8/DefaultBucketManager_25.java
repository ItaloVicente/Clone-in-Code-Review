
    @Override
    public IndexSettings insertSearchIndex(IndexSettings settings) {
        return insertSearchIndex(settings, timeout, TIMEOUT_UNIT);
    }

    @Override
    public IndexSettings insertSearchIndex(IndexSettings settings, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucketManager.insertSearchIndex(settings).single(), timeout, timeUnit);
    }

    @Override
    public IndexSettings updateSearchIndex(IndexSettings settings) {
        return updateSearchIndex(settings, timeout, TIMEOUT_UNIT);
    }

    @Override
    public IndexSettings updateSearchIndex(IndexSettings settings, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucketManager.updateSearchIndex(settings).single(), timeout, timeUnit);
    }

    @Override
    public Boolean hasSearchIndex(String name) {
        return hasSearchIndex(name, timeout, TIMEOUT_UNIT);
    }

    @Override
    public Boolean hasSearchIndex(String name, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucketManager.hasSearchIndex(name).single(), timeout, timeUnit);
    }

    @Override
    public Boolean removeSearchIndex(String name) {
        return removeSearchIndex(name, timeout, TIMEOUT_UNIT);
    }

    @Override
    public Boolean removeSearchIndex(String name, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucketManager.removeSearchIndex(name).single(), timeout, timeUnit);
    }
