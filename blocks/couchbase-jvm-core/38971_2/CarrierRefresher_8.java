
    @Override
    public void markTainted(final BucketConfig config) {
        if (subscriptions.containsKey(config.name())) {
            return;
        }

        LOGGER.debug("Config for bucket \"" + config.name() + "\" marked as tainted, starting polling.");
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(final Subscriber<? super Object> subscriber) {
                Subscription subscription = Schedulers.io().createWorker().schedulePeriodically(new Action0() {
                    @Override
                    public void call() {
                        GetBucketConfigRequest req = new GetBucketConfigRequest(config.name(), config.nodes().get(0).hostname());
                        cluster()
                            .<GetBucketConfigResponse>send(req)
                            .subscribe(new Action1<GetBucketConfigResponse>() {
                                @Override
                                public void call(GetBucketConfigResponse res) {
                                    provider().proposeBucketConfig(res.bucket(), res.content().toString(CharsetUtil.UTF_8));
                                }
                            });
                    }
                }, 0, 1, TimeUnit.SECONDS);

                subscriptions.put(config.name(), subscription);
            }
        }).subscribe();
    }

    @Override
    public void markUntainted(final BucketConfig config) {
        Subscription subscription = subscriptions.get(config.name());
        if (subscription != null) {
            LOGGER.debug("Config for bucket \"" + config.name() + "\" marked as untainted, stopping polling.");
            subscription.unsubscribe();
            subscriptions.remove(config.name());
        }
    }
