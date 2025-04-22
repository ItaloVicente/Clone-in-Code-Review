
    public int numberOfInfiniteStreams() {
        int infiniteStreams = 0;
        for (BucketStreamState streamState : feeds.values()) {
            if (streamState.endSequenceNumber() == 0xffffffff) {
                infiniteStreams++;
            }
        }
        return infiniteStreams;
    }
