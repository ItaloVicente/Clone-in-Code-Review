    @Override
    public QueryPlan queryPrepare(PrepareStatement prepare, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket
                .queryPrepare(prepare)
                .single(), timeout, timeUnit);
    }

