    public static final BucketStreamAggregatorStateListener NULL_LISTENER =
            new BucketStreamAggregatorStateListener() {
                @Override
                public void onDump(BucketStreamAggregatorState state) {
                }

                @Override
                public void onDump(BucketStreamAggregatorState aggregatorState, int partition,
                                   BucketStreamState streamState) {
                }
            };

    private final BucketStreamAggregatorStateListener listener;
    private BucketStreamState[] feeds;
