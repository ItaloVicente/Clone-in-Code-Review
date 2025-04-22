    public static Observable<GetResponse> read(final ClusterFacade core, final String id,
        final ReplicaMode type, final String bucket) {
        return assembleRequests(core, id, type, bucket)
            .flatMap(new Func1<BinaryRequest, Observable<GetResponse>>() {
                @Override
                public Observable<GetResponse> call(BinaryRequest request) {
                    return core
                        .<GetResponse>send(request)
                        .filter(GetResponseFilter.INSTANCE)
                        .onErrorResumeNext(GetResponseErrorHandler.INSTANCE);
