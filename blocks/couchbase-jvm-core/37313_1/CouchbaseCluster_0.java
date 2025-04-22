                })
                .subscribe(request.observable());
        } else if (request instanceof DisconnectRequest) {
            configProvider
                .closeBuckets()
                .flatMap(new Func1<ClusterConfig, Observable<Boolean>>() {
