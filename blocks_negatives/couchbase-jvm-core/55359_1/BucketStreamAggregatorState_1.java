    /**
     * Default state, which matches all changes in the stream.
     */
    public static final BucketStreamAggregatorState BLANK = new BucketStreamAggregatorState(0);

    private final PublishSubject<BucketStreamStateUpdatedEvent> updates;
    private BucketStreamState[] feeds;

    /**
     * Creates a new {@link BucketStreamAggregatorState}.
     *
     * @param feeds list containing state of each vBucket
     */
    public BucketStreamAggregatorState(final BucketStreamState[] feeds) {
        this.feeds = feeds;
        updates = PublishSubject.create();
    }
