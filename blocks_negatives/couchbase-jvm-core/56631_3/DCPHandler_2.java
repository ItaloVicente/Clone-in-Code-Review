    /**
     * Initializes a unique stream ID and stores it for later reference.
     *
     * @param bucket the name of the bucket.
     * @return the generated stream ID.
     */
    private int initializeUniqueStream(final String bucket) {
        int streamId = nextStreamId++;
        DCPStream stream = new DCPStream(env(), streamId, bucket);
        streams.put(streamId, stream);
        return streamId;
    }

