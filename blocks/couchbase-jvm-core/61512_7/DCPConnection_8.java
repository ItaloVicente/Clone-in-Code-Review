    private Observable<Integer> partitionSize() {
        return core
                .<GetClusterConfigResponse>send(new GetClusterConfigRequest())
                .map(new Func1<GetClusterConfigResponse, Integer>() {
                    @Override
                    public Integer call(GetClusterConfigResponse response) {
                        CouchbaseBucketConfig config = (CouchbaseBucketConfig) response.config().bucketConfig(bucket);
                        return config.numberOfPartitions();
                    }
                });
    }

        contexts.put(partition, ctx);
