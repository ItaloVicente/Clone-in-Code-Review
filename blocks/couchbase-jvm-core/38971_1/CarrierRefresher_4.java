
    @Override
    public void markTainted(final BucketConfig config) {
        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(final Subscriber<? super Object> subscriber) {
                Subscription subscription = Schedulers.io().createWorker().schedulePeriodically(new Action0() {
                    @Override
                    public void call() {
                        cluster().send(new GetBucketConfigRequest(config.name(), config.nodes().get(0).hostname()));
                    }
                }, 0, 1, TimeUnit.SECONDS);

                subscriptions.put(config.name(), subscription);
            }
        });
    }

    @Override
    public void markUntainted(final BucketConfig config) {
        Subscription subscription = subscriptions.get(config.name());
        if (subscription != null) {
            subscription.unsubscribe();
        }
        subscriptions.remove(config.name());
    }
