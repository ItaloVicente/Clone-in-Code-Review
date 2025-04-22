                    return deferAndWatch(new Func1<Subscriber, Observable<? extends BucketsConfigResponse>>() {
                        @Override
                        public Observable<? extends BucketsConfigResponse> call(Subscriber subscriber) {
                            BucketsConfigRequest request = new BucketsConfigRequest(username, password);
                            request.subscriber(subscriber);
                            return core.send(request);
                        }
                    });
