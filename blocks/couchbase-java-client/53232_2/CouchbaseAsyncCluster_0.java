        return Observable.defer(new Func0<Observable<OpenBucketResponse>>() {
                @Override
                public Observable<OpenBucketResponse> call() {
                    return core.send(new OpenBucketRequest(name, password));
                }
            })
