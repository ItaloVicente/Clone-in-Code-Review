    public Observable<Boolean> shutdown() {
        for (;;) {
            FixedSchedulerPool curr = pool.get();
            if (curr == NONE) {
                return Observable.just(true);
            }
            if (pool.compareAndSet(curr, NONE)) {
                curr.shutdown();
                return Observable.just(true);
