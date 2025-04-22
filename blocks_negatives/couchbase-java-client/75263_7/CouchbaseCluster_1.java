            .openBucket(name, password, transcoders)
            .map(new Func1<AsyncBucket, Bucket>() {
                @Override
                public Bucket call(AsyncBucket asyncBucket) {
                    CouchbaseBucket bucket = new CouchbaseBucket(asyncBucket, environment, core(), name, password);
                    bucketCache.put(name, bucket);
                    return bucket;
                }
            }).single(), timeout, timeUnit);
