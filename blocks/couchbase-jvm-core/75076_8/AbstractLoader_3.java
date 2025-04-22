    protected Observable<String> discoverConfig(String bucket, String password, InetAddress hostname) {
        return discoverConfig(bucket, bucket, password, hostname);
    }

    protected abstract Observable<String> discoverConfig(String bucket, String username, String password, InetAddress hostname);

    public Observable<Tuple2<LoaderType, BucketConfig>> loadConfig(final InetAddress seedNode, final String bucket,
                                                                   final String password) {
       return loadConfig(seedNode, bucket, bucket, password);
    }
