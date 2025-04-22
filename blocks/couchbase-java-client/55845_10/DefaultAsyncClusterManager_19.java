                .flatMap(new Func1<Boolean, Observable<RemoveBucketResponse>>() {
                    @Override
                    public Observable<RemoveBucketResponse> call(Boolean aBoolean) {
                        return core.send(new RemoveBucketRequest(name, username, password));
                    }
                }).map(new Func1<RemoveBucketResponse, Boolean>() {
