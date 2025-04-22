                        final GenericQueryRequest req = createN1qlRequest(query, bucket, username, password, hostname);
                        return deferAndWatch(new Func1<Subscriber, Observable<? extends GenericQueryResponse>>() {
                            @Override
                            public Observable<? extends GenericQueryResponse> call(Subscriber subscriber) {
                                req.subscriber(subscriber);
                                return core.send(req);
                            }
                        });
