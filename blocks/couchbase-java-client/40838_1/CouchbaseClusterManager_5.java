    public Observable<Boolean> removeBucket(final String name) {
        return
            ensureServiceEnabled()
            .flatMap(new Func1<Boolean, Observable<RemoveBucketResponse>>() {
                @Override
                public Observable<RemoveBucketResponse> call(Boolean aBoolean) {
                    return core.send(new RemoveBucketRequest(name, username, password));
                }
            }).map(new Func1<RemoveBucketResponse, Boolean>() {
                @Override
                public Boolean call(RemoveBucketResponse response) {
                    return response.status().isSuccess();
                }
            });
    }

    @Override
    public Observable<ClusterBucketSettings> insertBucket(final ClusterBucketSettings settings) {
        final StringBuilder sb = new StringBuilder();
        sb.append("name=").append(settings.name());
        sb.append("&ramQuotaMB=").append(settings.quota());
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
                    if (exists) {
                        throw new BucketExistsException("Bucket " + settings.name() + " already exists!");
                    }
                }
            }).flatMap(new Func1<Boolean, Observable<InsertBucketResponse>>() {
                @Override
                public Observable<InsertBucketResponse> call(Boolean exists) {
                    return core.send(new InsertBucketRequest(sb.toString(), username, password));
                }
            })
            .map(new Func1<InsertBucketResponse, ClusterBucketSettings>() {
                @Override
                public ClusterBucketSettings call(InsertBucketResponse response) {
                    return settings;
                }
            });
