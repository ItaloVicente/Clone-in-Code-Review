    @Override
    public boolean dropNamedPrimaryIndex(String customName, boolean ignoreIfNotExist) {
        return dropNamedPrimaryIndex(customName, ignoreIfNotExist, timeout, TIMEOUT_UNIT);
    }

    @Override
    public boolean dropNamedPrimaryIndex(String customName, boolean ignoreIfNotExist, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucketManager.dropNamedPrimaryIndex(customName, ignoreIfNotExist), timeout, timeUnit);
    }

