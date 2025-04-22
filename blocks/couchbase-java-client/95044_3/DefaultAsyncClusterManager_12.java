        return deferAndWatch(new Func1<Subscriber, Observable<? extends Boolean>>() {
            @Override
            public Observable<? extends Boolean> call(final Subscriber subscriber) {
                return
                    ensureServiceEnabled()
                        .flatMap(new Func1<Boolean, Observable<RemoveBucketResponse>>() {
                            @Override
                            public Observable<RemoveBucketResponse> call(Boolean aBoolean) {
                                RemoveBucketRequest req = new RemoveBucketRequest(name, username, password);
                                req.subscriber(subscriber);
                                return core.send(req);
                            }
                        })
                        .retryWhen(any().delay(Delay.fixed(100, TimeUnit.MILLISECONDS)).max(Integer.MAX_VALUE).build())
                        .map(new Func1<RemoveBucketResponse, Boolean>() {
                            @Override
                            public Boolean call(RemoveBucketResponse response) {
                                return response.status().isSuccess();
                            }
                        });
            }
        });

