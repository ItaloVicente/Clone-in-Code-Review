    /**
     * Maps stream identifiers to {@link DCPStream}. The identifiers put into
     * opaque fields of each packet of the stream and helps to multiplex streams.
     */
    private final Map<Integer, DCPStream> streams;

    /**
     * Counter for stream identifiers.
     */
    private int nextStreamId = 0;
