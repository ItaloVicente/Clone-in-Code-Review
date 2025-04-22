    public static int addStream(final String connectionName) {
        int newStreamId = nextStreamId++;
        streams.put(newStreamId, connectionName);
        return newStreamId;
    }

    public static String connectionName(final int streamId) {
        return streams.get(streamId);
    }

    public String name() {
        return name;
