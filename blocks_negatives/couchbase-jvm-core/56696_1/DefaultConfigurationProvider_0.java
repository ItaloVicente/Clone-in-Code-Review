        Observable<Tuple2<LoaderType, BucketConfig>> observable = loaderChain.get(0).loadConfig(seedHosts.get(),
            bucket, password);
        for (int i = 1; i < loaderChain.size(); i++) {
            observable = observable.onErrorResumeNext(
                loaderChain.get(i).loadConfig(seedHosts.get(), bucket, password)
            );
        }
