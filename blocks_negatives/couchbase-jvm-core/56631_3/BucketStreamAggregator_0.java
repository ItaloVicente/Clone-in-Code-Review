        return open(aggregatorState)
                .flatMap(new Func1<StreamRequestResponse, Observable<DCPRequest>>() {
                             @Override
                             public Observable<DCPRequest> call(StreamRequestResponse response) {
                                 return response.stream();
                             }
                         }
                );
    }

    /**
     * Opens DCP stream for all vBuckets starting with given state.
     * Use BucketStreamAggregatorState.BLANK to start from very beginning.
     *
     * @param aggregatorState state object
     * @return collection of stream objects
     */
    public Observable<StreamRequestResponse> open(final BucketStreamAggregatorState aggregatorState) {
