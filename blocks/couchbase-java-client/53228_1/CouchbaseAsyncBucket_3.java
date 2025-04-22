        return Observable.defer(new Func0<Observable<GetResponse>>() {
                @Override
                public Observable<GetResponse> call() {
                    return core.send(new GetRequest(id, bucket, false, true, expiry));
                }
            })
