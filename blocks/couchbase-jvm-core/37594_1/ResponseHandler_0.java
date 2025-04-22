
    private void scheduleForRetry(final CouchbaseRequest request) {
        Schedulers.computation().createWorker().schedule(new Action0() {
            @Override
            public void call() {
                cluster.send(request);
            }
        }, 10, TimeUnit.MILLISECONDS);
    }
