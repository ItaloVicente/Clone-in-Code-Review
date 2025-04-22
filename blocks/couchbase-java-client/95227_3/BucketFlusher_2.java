                public Observable<GetResponse> call(final String id) {
                    return deferAndWatch(new Func1<Subscriber, Observable<? extends GetResponse>>() {
                        @Override
                        public Observable<? extends GetResponse> call(Subscriber subscriber) {
                            GetRequest request = new GetRequest(id, bucket);
                            request.subscriber(subscriber);
                            return core.send(request);
                        }
                    });
