            .flatMap(new Func1<DisconnectResponse, Observable<Boolean>>() {
                @Override
                public Observable<Boolean> call(DisconnectResponse disconnectResponse) {
                    return sharedEnvironment ? Observable.just(true) : environment.shutdown();
                }
            });
