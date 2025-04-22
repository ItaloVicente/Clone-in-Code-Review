            .flatMap(new Func1<Boolean, Observable<BucketsConfigResponse>>() {
                @Override
                public Observable<BucketsConfigResponse> call(Boolean aBoolean) {
                    return core.send(new BucketsConfigRequest(username, password));
                }
            })
            .doOnNext(new Action1<BucketsConfigResponse>() {
                @Override
                public void call(BucketsConfigResponse response) {
                    if (!response.status().isSuccess()) {
                        if (response.config().contains("Unauthorized")) {
                            throw new InvalidPasswordException();
                        } else {
                            throw new CouchbaseException(response.status() + ": " + response.config());
                        }
