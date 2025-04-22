        Observable<Tuple2<LoaderType, BucketConfig>> observable = Observable.mergeDelayError(Observable
            .from(seedHosts)
            .map(new Func1<InetAddress, Observable<Tuple2<LoaderType, BucketConfig>>>() {
                @Override
                public Observable<Tuple2<LoaderType, BucketConfig>> call(InetAddress seedHost) {
                    Observable<Tuple2<LoaderType, BucketConfig>> node = loaderChain.get(0)
                        .loadConfig(seedHost, bucket, password);
                    for (int i = 1; i < loaderChain.size(); i++) {
                        node = node.onErrorResumeNext(loaderChain.get(i)
                            .loadConfig(seedHost, bucket, password));
                    }
                    return node;
                }
            })
        )
        .take(1);
