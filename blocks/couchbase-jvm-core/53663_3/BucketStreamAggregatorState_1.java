        if (notify) {
            listener.onDump(this, partition, state);
        }
    }

    public void replace(final BucketStreamState[] feeds) {
        replace(feeds, true);
    }

    public void replace(final BucketStreamState[] feeds, boolean notify) {
        this.feeds = feeds;
        if (notify) {
            listener.onDump(this);
        }
