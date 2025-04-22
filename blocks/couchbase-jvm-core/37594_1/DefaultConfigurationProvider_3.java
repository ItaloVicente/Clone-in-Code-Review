
        Observable<BucketConfig> observable = loaderChain.get(0).loadConfig(seedHosts.get(), bucket, password);
        for (int i = 1; i < loaderChain.size(); i++) {
            Observable<BucketConfig> nested = loaderChain.get(i).loadConfig(seedHosts.get(), bucket, password);
            observable = observable.onErrorResumeNext(nested);
        }

        return
            observable
