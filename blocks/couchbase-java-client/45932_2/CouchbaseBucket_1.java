    @Override
    public QueryPlan prepare(String statement, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket
            .prepare(statement)
            .single(), timeout, timeUnit);
    }
