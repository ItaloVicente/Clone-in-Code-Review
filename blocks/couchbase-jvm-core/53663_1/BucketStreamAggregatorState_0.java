    public static final BucketStreamAggregatorStateSerializer NULL_SERIALIZER =
            new BucketStreamAggregatorStateSerializer() {
                @Override
                public void dump(BucketStreamAggregatorState state) {
                }

                @Override
                public void dump(BucketStreamAggregatorState aggregatorState, int partition,
                                 BucketStreamState streamState) {
                }

                @Override
                public BucketStreamAggregatorState load(BucketStreamAggregatorState aggregatorState) {
                    return BucketStreamAggregatorState.BLANK;
                }

                @Override
                public BucketStreamState load(BucketStreamAggregatorState aggregatorState, int partition) {
                    return BucketStreamState.BLANK;
                }
            };

    private final BucketStreamAggregatorStateSerializer serializer;
    private BucketStreamState[] feeds;
