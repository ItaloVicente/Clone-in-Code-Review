    public int addStream(final String connectionName) {
        int streamId = nextStreamId++;
        streams.add(streamId);
        streamRegistry.put(streamId, connectionName);
        return streamId;
    }

    public void removeStream(final int streamId) {
        streams.remove((Integer) streamId);
        streamRegistry.remove(streamId);
    }

    public int streamsCount() {
        return streams.size();
