            .flatMap(new Func1<BinaryRequest, Observable<GetResponse>>() {
                @Override
                public Observable<GetResponse> call(BinaryRequest request) {
                    return core
                        .<GetResponse>send(request)
                        .filter(GetResponseFilter.INSTANCE)
                        .onErrorResumeNext(GetResponseErrorHandler.INSTANCE);
                }
            });
