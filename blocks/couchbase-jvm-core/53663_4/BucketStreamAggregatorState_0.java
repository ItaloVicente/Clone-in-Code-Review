    public PublishSubject<BucketStreamStateUpdatedEvent> updates() {
        return updates;
    }

    public int numPartitions() {
        return feeds.length;
    }

