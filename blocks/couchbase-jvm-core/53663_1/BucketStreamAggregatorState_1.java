        if (dump) {
            serializer.dump(this, partition, state);
        }
    }

    public void replace(final BucketStreamState[] feeds) {
        replace(feeds, true);
    }

    public void replace(final BucketStreamState[] feeds, boolean dump) {
        this.feeds = feeds;
        if (dump) {
            serializer.dump(this);
        }
