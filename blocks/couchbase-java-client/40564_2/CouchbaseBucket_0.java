    @Override
    public Observable<LongDocument> counter(String id, long delta) {
        return counter(id, delta, delta);
    }

    @Override
    public Observable<LongDocument> counter(String id, long delta, long initial) {
        return counter(id, delta, initial, 0);
    }

