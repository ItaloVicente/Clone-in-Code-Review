        if (notify) {
            updates.onNext(new BucketStreamStateUpdatedEvent(this, partition));
        }
    }

    public void replace(final BucketStreamState[] feeds) {
        replace(feeds, true);
    }

    public void replace(final BucketStreamState[] feeds, boolean notify) {
        this.feeds = feeds;
        if (notify) {
            updates.onNext(new BucketStreamStateUpdatedEvent(this));
        }
