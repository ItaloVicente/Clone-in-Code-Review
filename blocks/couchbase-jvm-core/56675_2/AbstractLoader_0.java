            .from(seedNodes)
            .map(new Func1<InetAddress, Observable<Tuple3<LoaderType, BucketConfig, Throwable>>>() {
                @Override
                public Observable<Tuple3<LoaderType, BucketConfig, Throwable>> call(InetAddress inetAddress) {
                    return maskPassthroughErrors(loadConfigAtAddr(inetAddress, bucket, password));
                }
            })
        )
        .take(1)
        .flatMap(UnmaskPassthroughErrors.INSTANCE);
