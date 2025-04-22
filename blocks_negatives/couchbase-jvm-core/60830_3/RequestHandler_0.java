            final CouchbaseRequest request = event.getRequest();

            ClusterConfig config = configuration;
            if (!(request instanceof BootstrapMessage)) {
                BucketConfig bucketConfig = config == null ? null : config.bucketConfig(request.bucket());
                if (config == null || (request.bucket() != null  && bucketConfig == null)) {
                    request.observable().onError(new BucketClosedException(request.bucket() + " has been closed"));
                    return;
                }

                try {
                    checkFeaturesForRequest(request, bucketConfig);
                } catch (ServiceNotAvailableException e) {
                    request.observable().onError(e);
                    return;
                }
