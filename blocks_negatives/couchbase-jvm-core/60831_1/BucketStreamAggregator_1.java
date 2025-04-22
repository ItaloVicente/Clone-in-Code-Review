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
