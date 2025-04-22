                        return deferAndWatch(new Func1<Subscriber, Observable<? extends RemoveBucketResponse>>() {
                            @Override
                            public Observable<? extends RemoveBucketResponse> call(Subscriber subscriber) {
                                RemoveBucketRequest request = new RemoveBucketRequest(name, username, password);
                                request.subscriber(subscriber);
                                return core.send(request);
                            }
                        });
