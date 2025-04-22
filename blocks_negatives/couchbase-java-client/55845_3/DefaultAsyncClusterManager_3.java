            .flatMap(new Func1<Boolean, Observable<ClusterConfigResponse>>() {
                @Override
                public Observable<ClusterConfigResponse> call(Boolean aBoolean) {
                    return core.send(new ClusterConfigRequest(username, password));
                }
            })
            .doOnNext(new Action1<ClusterConfigResponse>() {
                @Override
                public void call(ClusterConfigResponse response) {
                    if (!response.status().isSuccess()) {
                        if (response.config().contains("Unauthorized")) {
                            throw new InvalidPasswordException();
                        } else {
                            throw new CouchbaseException(response.status() + ": " + response.config());
