                    public Observable<BucketStreamAggregatorState> call(final Integer numPartitions) {
                        return Observable.range(0, numPartitions).flatMap(new Func1<Integer, Observable<GetFailoverLogResponse>>() {
                            @Override
                            public Observable<GetFailoverLogResponse> call(final Integer partition) {
                                return core.send(new GetFailoverLogRequest(partition.shortValue(), bucket));
                            }
                        }).flatMap(new Func1<GetFailoverLogResponse, Observable<BucketStreamState>>() {
                            @Override
                            public Observable<BucketStreamState> call(final GetFailoverLogResponse failoverLogsResponse) {
                                final FailoverLogEntry entry = failoverLogsResponse.failoverLog().get(0);
                                return core.<GetLastCheckpointResponse>send(new GetLastCheckpointRequest(failoverLogsResponse.partition(), bucket))
                                        .map(new Func1<GetLastCheckpointResponse, BucketStreamState>() {
                                            @Override
                                            public BucketStreamState call(GetLastCheckpointResponse lastCheckpointResponse) {
                                                return new BucketStreamState(
                                                        failoverLogsResponse.partition(),
                                                        entry.vbucketUUID(),
                                                        lastCheckpointResponse.sequenceNumber());
                                            }
                                        });
                            }
                        }).collect(new Func0<BucketStreamAggregatorState>() {
                            @Override
                            public BucketStreamAggregatorState call() {
                                return new BucketStreamAggregatorState();
                            }
                        }, new Action2<BucketStreamAggregatorState, BucketStreamState>() {
                            @Override
                            public void call(BucketStreamAggregatorState aggregatorState, BucketStreamState streamState) {
                                aggregatorState.put(streamState);
                            }
                        });
