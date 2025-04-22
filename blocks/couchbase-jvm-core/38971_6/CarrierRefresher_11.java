    public void markUntainted(final BucketConfig config) {
        Subscription subscription = subscriptions.get(config.name());
        if (subscription != null) {
            LOGGER.debug("Config for bucket \"" + config.name() + "\" marked as untainted, stopping polling.");
            subscription.unsubscribe();
            subscriptions.remove(config.name());
        }
    }

    @Override
    public void refresh(final ClusterConfig config) {
        Observable
            .from(config.bucketConfigs().values())
            .flatMap(new Func1<BucketConfig, Observable<GetBucketConfigResponse>>() {
                @Override
                public Observable<GetBucketConfigResponse> call(BucketConfig config) {
                    GetBucketConfigRequest req = new GetBucketConfigRequest(config.name(), config.nodes().get(0).hostname());
                    return cluster().send(req);
                }
            }).subscribe(new Action1<GetBucketConfigResponse>() {
                @Override
                public void call(GetBucketConfigResponse res) {
                    provider().proposeBucketConfig(res.bucket(), res.content().toString(CharsetUtil.UTF_8));
                }
             });
