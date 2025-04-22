     * @param partition vBucketID (partition number)
     * @param state     stream state
     * @param notify    false if state notification should be skipped
     * @throws IndexOutOfBoundsException if the state holder is BLANK, or allocated less
     *                                   partition slots then requested index.
     */
    public void set(int partition, final BucketStreamState state, boolean notify) {
        feeds[partition] = state;
        if (notify) {
            updates.onNext(new BucketStreamStateUpdatedEvent(this, partition));
        }
    }

    /**
     * Replaces whole aggregator state and optionally notifies listener.
     *
     * @param feeds new state of partitions.
     */
    public void replace(final BucketStreamState[] feeds) {
        replace(feeds, true);
    }

    /**
     * Replaces whole aggregator state and optionally notifies listener.
     *
     * @param feeds  new state of partitions.
