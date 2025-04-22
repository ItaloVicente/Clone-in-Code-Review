    /**
     * Try to bootstrap from one of the given seed nodes through the carrier publication binary mechanism.
     *
     * @param bucket
     * @param password
     * @return
     */
    private Observable<BucketConfig> bootstrapThroughCarrierPublication(final String bucket, final String password) {
        return Observable
            .from(seedHosts.get(), Schedulers.computation())
            .flatMap(new Func1<InetAddress, Observable<AddNodeResponse>>() {
                @Override
                public Observable<AddNodeResponse> call(InetAddress addr) {
                    return cluster.send(new AddNodeRequest(addr.getHostName()));
                }
            }).flatMap(new Func1<AddNodeResponse, Observable<AddServiceResponse>>() {
                @Override
                public Observable<AddServiceResponse> call(AddNodeResponse response) {
                    int port = environment.sslEnabled()
                        ? environment.bootstrapCarrierSslPort() : environment.bootstrapCarrierDirectPort();
                    return cluster.send(new AddServiceRequest(ServiceType.BINARY, bucket, password, port, response.hostname()));
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
                        String rawConfig = response.content().replace("$HOST", response.hostname());
                        BucketConfig config =  objectMapper.readValue(rawConfig, BucketConfig.class);
                        config.password(password);
                        return config;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
    }

    /**
     * Try to bootstrap from the given seed nodes through HTTP.
     *
     * @param bucket
     * @param password
     * @return
     */
    private Observable<BucketConfig> bootstrapThroughHttp(final String bucket, final String password) {
        return null;
