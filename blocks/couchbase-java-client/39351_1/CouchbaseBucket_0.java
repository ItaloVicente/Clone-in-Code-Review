
    @Override
    public Observable<LongDocument> counter(final String id, final long delta, final long initial, final int expiry) {
        core.<CounterResponse>send(new CounterRequest(id, delta, initial, expiry, bucket))
        return null;
    }
