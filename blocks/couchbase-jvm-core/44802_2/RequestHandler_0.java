        try {
            final CouchbaseRequest request = event.getRequest();

            if (!(request instanceof BootstrapMessage)) {
                ClusterConfig config = configuration.get();
                if (config == null || (request.bucket() != null  && !config.hasBucket(request.bucket()))) {
                    request.observable().onError(new BucketClosedException(request.bucket() + " has been closed"));
                    return;
                }
