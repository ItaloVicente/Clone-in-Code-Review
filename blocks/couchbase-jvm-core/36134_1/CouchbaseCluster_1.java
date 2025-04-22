    private void handleClusterRequest(final CouchbaseRequest request) {
        if (request instanceof SeedNodesRequest) {
            boolean success = configProvider.seedHosts(((SeedNodesRequest) request).nodes());
            request.observable().onNext(new SeedNodesResponse(success));
            request.observable().onCompleted();
        } else if (request instanceof OpenBucketRequest) {
            configProvider.openBucket(request.bucket(), ((OpenBucketRequest) request).password()).subscribe(
                new Observer<ClusterConfig>() {
                    @Override
                    public void onCompleted() {
                        request.observable().onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        request.observable().onError(e);
                    }

                    @Override
                    public void onNext(ClusterConfig clusterConfig) {
                        request.observable().onNext(new OpenBucketResponse());
                    }
                }
            );
        }
    }

