    public Observable<BucketStreamStateUpdatedEvent> updates() {
        return updates;
    }

    public int numPartitions() {
        return feeds.length;
    }

