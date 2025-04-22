                @Override
                public Observable<OpenBucketResponse> call() {
                    return core.send(new OpenBucketRequest(name, pass));
                }
            })
            .map(new Func1<CouchbaseResponse, AsyncBucket>() {
                @Override
                public AsyncBucket call(CouchbaseResponse response) {
                    if (response.status() != ResponseStatus.SUCCESS) {
                        throw new CouchbaseException("Could not open bucket.");
                    }

                    AsyncBucket bucket = new CouchbaseAsyncBucket(core, environment, name, pass, trans);
                    bucketCache.put(name, bucket);
                    return bucket;
