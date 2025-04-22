        } else if (request instanceof CloseBucketRequest) {
            configProvider
                .closeBucket(request.bucket())
                .flatMap(new Func1<ClusterConfig, Observable<ClusterConfig>>() {
                    @Override
                    public Observable<ClusterConfig> call(ClusterConfig clusterConfig) {
                        return requestHandler.reconfigure(clusterConfig);
                    }
                })
                .map(new Func1<ClusterConfig, CloseBucketResponse>() {
                    @Override
                    public CloseBucketResponse call(ClusterConfig clusterConfig) {
                        if (!clusterConfig.hasBucket(request.bucket())) {
                            return new CloseBucketResponse(ResponseStatus.SUCCESS);
                        }
                        throw new CouchbaseException("Could not close bucket.");
                    }
                })
                .subscribe(request.observable());
