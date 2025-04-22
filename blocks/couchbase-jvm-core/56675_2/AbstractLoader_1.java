
    private Observable<Tuple3<LoaderType, BucketConfig, Throwable>> maskPassthroughErrors(
        final Observable<Tuple2<LoaderType, BucketConfig>> input) {
        return input
            .map(new Func1<Tuple2<LoaderType, BucketConfig>, Tuple3<LoaderType, BucketConfig, Throwable>>() {
                @Override
                public Tuple3<LoaderType, BucketConfig, Throwable> call(Tuple2<LoaderType, BucketConfig> in) {
                    return Tuple.create(in.value1(), in.value2(), null);
                }
            })
            .onErrorResumeNext(new Func1<Throwable, Observable<Tuple3<LoaderType, BucketConfig, Throwable>>>() {
                @Override
                public Observable<Tuple3<LoaderType, BucketConfig, Throwable>> call(Throwable throwable) {
                    if (throwable instanceof LoadingFailedException) {
                        return Observable.just(Tuple.create(loaderType, (BucketConfig) null, throwable));
                    }
                    return Observable.error(throwable);
                }
            });
    }

    private static class UnmaskPassthroughErrors implements
        Func1<Tuple3<LoaderType, BucketConfig, Throwable>, Observable<Tuple2<LoaderType, BucketConfig>>> {

        private static UnmaskPassthroughErrors INSTANCE = new UnmaskPassthroughErrors();

        @Override
        public Observable<Tuple2<LoaderType, BucketConfig>> call(Tuple3<LoaderType, BucketConfig, Throwable> in) {
            if (in.value3() == null) {
                return Observable.just(Tuple.create(in.value1(), in.value2()));
            }
            return Observable.error(in.value3());
        }

    }
