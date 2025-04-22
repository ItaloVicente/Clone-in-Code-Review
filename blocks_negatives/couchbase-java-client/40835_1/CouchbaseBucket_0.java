    @Override
    public Observable<BucketInfo> info() {
        return core
            .<BucketConfigResponse>send(new BucketConfigRequest("/pools/default/buckets/", null, bucket, password))
            .map(new Func1<BucketConfigResponse, BucketInfo>() {
                @Override
                public BucketInfo call(BucketConfigResponse response) {
                    try {
                        return DefaultBucketInfo.create(JSON_TRANSCODER.stringToJsonObject(response.config()));
                    } catch (Exception ex) {
                        throw new CouchbaseException("Could not parse bucket info.", ex);
                    }
                }
            });
    }

