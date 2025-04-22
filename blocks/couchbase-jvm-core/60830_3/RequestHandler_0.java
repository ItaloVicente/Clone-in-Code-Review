    private void dispatchRequest(final CouchbaseRequest request) {
        ClusterConfig config = configuration;

        if (!(request instanceof BootstrapMessage)) {
            BucketConfig bucketConfig = config == null ? null : config.bucketConfig(request.bucket());
            if (config == null || (request.bucket() != null  && bucketConfig == null)) {
                request.observable().onError(new BucketClosedException(request.bucket() + " has been closed"));
