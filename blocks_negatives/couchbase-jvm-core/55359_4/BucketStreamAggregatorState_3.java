    /**
     * Replaces whole aggregator state and optionally notifies listener.
     *
     * @param feeds  new state of partitions.
     * @param notify false if state notification should be skipped
     */
    public void replace(final BucketStreamState[] feeds, boolean notify) {
        this.feeds = feeds;
        if (notify) {
            updates.onNext(new BucketStreamStateUpdatedEvent(this));
        }
