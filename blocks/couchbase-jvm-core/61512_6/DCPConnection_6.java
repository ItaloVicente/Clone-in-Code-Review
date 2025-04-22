    public Observable<MutationToken> getCurrentState() {
        return partitionSize().flatMap(new Func1<Integer, Observable<MutationToken>>() {
            @Override
            public Observable<MutationToken> call(final Integer numPartitions) {
                return Observable.range(0, numPartitions).flatMap(new Func1<Integer, Observable<GetFailoverLogResponse>>() {
                    @Override
                    public Observable<GetFailoverLogResponse> call(final Integer partition) {
                        return core.send(new GetFailoverLogRequest(partition.shortValue(), bucket));
                    }
                }).flatMap(new Func1<GetFailoverLogResponse, Observable<MutationToken>>() {
                    @Override
                    public Observable<MutationToken> call(final GetFailoverLogResponse failoverLogsResponse) {
                        final FailoverLogEntry entry = failoverLogsResponse.failoverLog().get(0);
                        return core.<GetLastCheckpointResponse>send(new GetLastCheckpointRequest(failoverLogsResponse.partition(), bucket))
                                .map(new Func1<GetLastCheckpointResponse, MutationToken>() {
                                    @Override
                                    public MutationToken call(GetLastCheckpointResponse lastCheckpointResponse) {
                                        return new MutationToken(
                                                failoverLogsResponse.partition(),
                                                entry.vbucketUUID(),
                                                lastCheckpointResponse.sequenceNumber(),
                                                bucket);
                                    }
                                });
                    }
                });
            }
        });
