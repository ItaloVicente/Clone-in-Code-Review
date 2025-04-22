                    return deferAndWatch(new Func1<Subscriber, Observable<? extends InsertBucketResponse>>() {
                        @Override
                        public Observable<? extends InsertBucketResponse> call(Subscriber subscriber) {
                            InsertBucketRequest request = new InsertBucketRequest(payload, username, password);
                            request.subscriber(subscriber);
                            return core.send(request);
                        }
                    });
