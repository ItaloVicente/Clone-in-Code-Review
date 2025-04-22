                    return deferAndWatch(new Func1<Subscriber, Observable<? extends UpdateBucketResponse>>() {
                        @Override
                        public Observable<? extends UpdateBucketResponse> call(Subscriber subscriber) {
                            UpdateBucketRequest request = new UpdateBucketRequest(
                                settings.name(), payload, username, password);
                            request.subscriber(subscriber);
                            return core.send(request);
                        }
                    });
