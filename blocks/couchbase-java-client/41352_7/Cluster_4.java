    Bucket openBucket(String name, String password);

    Bucket openBucket(String name, String password, long timeout, TimeUnit timeUnit);

    Bucket openBucket(String name, String password, List<Transcoder<? extends Document, ?>> transcoders);

    Bucket openBucket(String name, String password, List<Transcoder<? extends Document, ?>> transcoders,
        long timeout, TimeUnit timeUnit);


    ClusterManager clusterManager(String username, String password);

