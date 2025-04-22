    @Override
    public PreparedPayload prepare(String statement, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket
            .prepare(statement)
            .single(), timeout, timeUnit);
    }
    @Override
    public PreparedPayload prepare(Statement statement, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket
            .prepare(statement)
            .single(), timeout, timeUnit);
    }
