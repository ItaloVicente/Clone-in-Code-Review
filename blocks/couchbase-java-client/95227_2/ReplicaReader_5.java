                .flatMap(new Func1<BinaryRequest, Observable<GetResponse>>() {
                    @Override
                    public Observable<GetResponse> call(final BinaryRequest request) {
                        Observable<GetResponse> result = deferAndWatch(new Func1<Subscriber, Observable<GetResponse>>() {
                            @Override
                            public Observable<GetResponse> call(Subscriber subscriber) {
                                request.subscriber(subscriber);
                                return core.send(request);
                            }
                        }).filter(GetResponseFilter.INSTANCE);
                        return result.onErrorResumeNext(GetResponseErrorHandler.INSTANCE);
                    }
                });
