    public Observable<BucketStreamAggregatorState> getCurrentState() {
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
                    @Override
                    public Observable<BucketStreamAggregatorState> call(final BucketStreamAggregatorState aggregatorState) {
                        return Observable
                                .from(aggregatorState)
                                .flatMap(new Func1<BucketStreamState, Observable<GetFailoverLogResponse>>() {
                                    @Override
                                    public Observable<GetFailoverLogResponse> call(BucketStreamState streamState) {
                                        return core.send(new GetFailoverLogRequest(streamState.partition(), bucket));
                                    }
                                })
                                .collect(new Func0<BucketStreamAggregatorState>() {
                                    @Override
                                    public BucketStreamAggregatorState call() {
                                        return aggregatorState;
                                    }
                                }, new Action2<BucketStreamAggregatorState, GetFailoverLogResponse>() {
                                    @Override
                                    public void call(BucketStreamAggregatorState state, GetFailoverLogResponse response) {
                                        final FailoverLogEntry entry = response.failoverLog().get(0);
                                        state.put(new BucketStreamState(response.partition(), entry.vbucketUUID(),
                                                state.get(response.partition()).startSequenceNumber()));
                                    }
                                });
                    }
                });
    }

    private Observable<DCPConnection> open() {
        if (connection == null) {
            return core.<OpenConnectionResponse>send(new OpenConnectionRequest(name, bucket))
                    .flatMap(new Func1<OpenConnectionResponse, Observable<DCPConnection>>() {
                        @Override
                        public Observable<DCPConnection> call(final OpenConnectionResponse response) {
                            connection = response.connection();
                            return Observable.just(connection);
                        }
                    });
        }
        return Observable.just(connection);
    }

