    @Override
    public boolean createNamedPrimaryIndex(String customName, boolean ignoreIfExist, boolean defer) {
        return createNamedPrimaryIndex(customName, ignoreIfExist, defer, timeout, TIMEOUT_UNIT);
    }

    @Override
    public boolean createNamedPrimaryIndex(String customName, boolean ignoreIfExist, boolean defer, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucketManager.createNamedPrimaryIndex(customName, ignoreIfExist, defer), timeout, timeUnit);
    }

