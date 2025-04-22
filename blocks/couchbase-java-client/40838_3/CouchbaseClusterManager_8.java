    public Observable<ClusterBucketSettings> updateBucket(final ClusterBucketSettings settings) {
        final StringBuilder sb = new StringBuilder();
        sb.append("ramQuotaMB=").append(settings.quota());
        sb.append("&authType=").append("sasl");
        sb.append("&saslPassword=").append(settings.password());
        sb.append("&replicaNumber=").append(settings.replicas());
        sb.append("&proxyPort=").append(settings.port());
        sb.append("&bucketType=").append(settings.type() == BucketType.COUCHBASE ? "membase" : "memcached");
        sb.append("&flushEnabled=").append(settings.enableFlush() ? "1" : "0");

        return hasBucket(settings.name())
            .doOnNext(new Action1<Boolean>() {
                @Override
                public void call(Boolean exists) {
                    if(!exists) {
                        throw new BucketDoesNotExistException("Bucket " + settings.name() + " does not exist!");
                    }
                }
            }).flatMap(new Func1<Boolean, Observable<UpdateBucketResponse>>() {
                @Override
                public Observable<UpdateBucketResponse> call(Boolean exists) {
                    return core.send(new UpdateBucketRequest(settings.name(), sb.toString(), username, password));
                }
            }).map(new Func1<UpdateBucketResponse, ClusterBucketSettings>() {
                @Override
                public ClusterBucketSettings call(UpdateBucketResponse response) {
                    if (!response.status().isSuccess()) {
                        throw new CouchbaseException("Could not update bucket: " + response.config());
                    }
                    return settings;
                }
            });
    }
