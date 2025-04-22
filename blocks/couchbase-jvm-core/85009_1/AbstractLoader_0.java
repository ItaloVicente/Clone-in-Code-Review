            }).onErrorResumeNext(new Func1<Throwable, Observable<? extends Tuple2<LoaderType, BucketConfig>>>() {
                @Override
                public Observable<? extends Tuple2<LoaderType, BucketConfig>> call(final Throwable throwable) {
                    return cluster
                        .<RemoveServiceResponse>send(new RemoveServiceRequest(serviceType, bucket, node))
                        .flatMap(new Func1<RemoveServiceResponse, Observable<LifecycleState>>() {
                            @Override
                            public Observable<LifecycleState> call(RemoveServiceResponse response) {
                                return response.service().disconnect();
                            }
                        })
                        .flatMap(new Func1<LifecycleState, Observable<Tuple2<LoaderType, BucketConfig>>>() {
                            @Override
                            public Observable<Tuple2<LoaderType, BucketConfig>> call(LifecycleState lifecycleState) {
                                return Observable.error(throwable);
                            }
                        });
                }
