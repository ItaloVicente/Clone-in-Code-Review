        return open()
                .flatMap(new Func1<DCPConnection, Observable<GetAllMutationTokensResponse>>() {
                    @Override
                    public Observable<GetAllMutationTokensResponse> call(final DCPConnection response) {
                        return core.send(new GetAllMutationTokensRequest(bucket));
                    }
                })
                .flatMap(new Func1<GetAllMutationTokensResponse, Observable<BucketStreamAggregatorState>>() {
                    @Override
                    public Observable<BucketStreamAggregatorState> call(final GetAllMutationTokensResponse response) {
                        BucketStreamAggregatorState state = new BucketStreamAggregatorState();
                        for (MutationToken token : response.mutationTokens()) {
                            state.put(new BucketStreamState((short) token.vbucketID(), token.vbucketUUID(), token.sequenceNumber()));
                        }
                        return Observable.just(state);
                    }
                })
                .flatMap(new Func1<BucketStreamAggregatorState, Observable<BucketStreamAggregatorState>>() {
