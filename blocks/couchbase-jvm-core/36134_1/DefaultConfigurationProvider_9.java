        return configObservable;
    }

    @Override
    public boolean seedHosts(final List<String> hosts) {
        if (bootstrapped) {
            LOGGER.debug("Seed hosts called with {}, but already bootstrapped.", hosts);
            return false;
        }
        LOGGER.debug("Setting seed hosts to {}", hosts);
        this.seedHosts.set(hosts);
        return true;
    }

    @Override
    public Observable<ClusterConfig> openBucket(final String bucket, final String password) {
        if (currentConfig.get() != null && currentConfig.get().hasBucket(bucket)) {
            return Observable.from(currentConfig.get());
        }

        return bootstrapThroughCarrierPublication(bucket, password).map(new Func1<BucketConfig, ClusterConfig>() {
            @Override
            public ClusterConfig call(final BucketConfig bucketConfig) {
                applyBucketConfig(bucket, bucketConfig);
                return currentConfig.get();
            }
        });
    }

    private Observable<BucketConfig> bootstrapThroughCarrierPublication(final String bucket, final String password) {
        return Observable.from(seedHosts.get()).flatMap(new Func1<String, Observable<AddNodeResponse>>() {
            @Override
            public Observable<AddNodeResponse> call(String hostname) {
                return cluster.send(new AddNodeRequest(hostname));
            }
        }).flatMap(new Func1<AddNodeResponse, Observable<AddServiceResponse>>() {
            @Override
            public Observable<AddServiceResponse> call(AddNodeResponse response) {
                return cluster.send(new AddServiceRequest(ServiceType.BINARY, bucket, response.hostname()));
            }
        }).flatMap(new Func1<AddServiceResponse, Observable<GetBucketConfigResponse>>() {
            @Override
            public Observable<GetBucketConfigResponse> call(AddServiceResponse response) {
                GetBucketConfigRequest request = new GetBucketConfigRequest(bucket, response.hostname());
                return cluster.send(request);
            }
        }).map(new Func1<GetBucketConfigResponse, BucketConfig>() {
            @Override
            public BucketConfig call(GetBucketConfigResponse response) {
                try {
                    String config = response.content().replace("$HOST", response.hostname());
                    return objectMapper.readValue(config, BucketConfig.class);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private Observable<BucketConfig> bootstrapThroughHttp(final String bucket, final String password) {
