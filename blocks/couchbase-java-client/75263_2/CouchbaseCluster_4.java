        final Credential userCred = cred;

        return Blocking.blockForSingle(couchbaseAsyncCluster
                .openBucket(name, transcoders)
                .map(new Func1<AsyncBucket, Bucket>() {
                    @Override
                    public Bucket call(AsyncBucket asyncBucket) {
                        CouchbaseBucket bucket = new CouchbaseBucket(asyncBucket, environment, core(), name,
                                userCred.login(), userCred.password());
                        bucketCache.put(name, bucket);
                        return bucket;
                    }
                }).single(), timeout, timeUnit);
