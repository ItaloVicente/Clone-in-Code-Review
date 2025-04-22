                return deferAndWatch(new Func1<Subscriber, Observable<? extends BucketConfigResponse>>() {
                    @Override
                    public Observable<? extends BucketConfigResponse> call(Subscriber subscriber) {
                        BucketConfigRequest request = new BucketConfigRequest("/pools/default/buckets/",
                            null, bucket, username, password);
                        request.subscriber(subscriber);
                        return core.send(request);
                    }
                });
