    public Observable<LongDocument> counter(final String id, final long delta, final long initial, final int expiry) {
        return core
            .<CounterResponse>send(new CounterRequest(id, initial, delta, expiry, bucket))
            .map(new Func1<CounterResponse, LongDocument>() {
                @Override
                public LongDocument call(CounterResponse response) {
                    return LongDocument.create(id, expiry, response.value(), response.cas());
                }
            });
