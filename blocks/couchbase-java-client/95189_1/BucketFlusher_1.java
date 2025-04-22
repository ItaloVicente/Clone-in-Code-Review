        return deferAndWatch(new Func1<Subscriber, Observable<FlushResponse>>() {
                @Override
                public Observable<FlushResponse> call(Subscriber subscriber) {
                    FlushRequest request = new FlushRequest(bucket, username, password);
                    request.subscriber(subscriber);
                    return core.send(request);
                }
            })
