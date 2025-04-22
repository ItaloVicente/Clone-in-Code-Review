        return Observable
            .just(seedNode)
            .flatMap(new Func1<InetAddress, Observable<Tuple2<LoaderType, BucketConfig>>>() {
                @Override
                public Observable<Tuple2<LoaderType, BucketConfig>> call(InetAddress inetAddress) {
                    return loadConfigAtAddr(inetAddress, bucket, password);
                }
            });
