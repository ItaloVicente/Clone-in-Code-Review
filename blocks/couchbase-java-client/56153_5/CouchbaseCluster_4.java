        return Blocking.blockForSingle(
            couchbaseAsyncCluster
                .disconnect()
                .doOnNext(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        bucketCache.clear();
                    }
                }),
            timeout,
            timeUnit
        );
