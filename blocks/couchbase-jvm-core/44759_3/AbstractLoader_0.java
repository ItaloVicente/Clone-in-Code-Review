
        return Observable.mergeDelayError(Observable
                .from(seedNodes)
                .map(new Func1<InetAddress, Observable<Tuple2<LoaderType, BucketConfig>>>() {
                    @Override
                    public Observable<Tuple2<LoaderType, BucketConfig>> call(InetAddress inetAddress) {
                        return loadConfigAtAddr(inetAddress, bucket, password);
                    }
                })
            )
            .take(1);
    }

    private Observable<Tuple2<LoaderType, BucketConfig>> loadConfigAtAddr(final InetAddress node, final String bucket,
        final String password) {
