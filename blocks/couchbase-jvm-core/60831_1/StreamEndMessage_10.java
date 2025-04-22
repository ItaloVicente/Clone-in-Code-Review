    public StreamEndMessage(DCPConnection connection, int totalBodyLength, short partition, final Reason reason, String bucket) {
        this(connection, totalBodyLength, partition, reason, bucket, null);
    }

    public StreamEndMessage(DCPConnection connection, int totalBodyLength, short partition, final Reason reason, final String bucket, final String password) {
        super(connection, totalBodyLength, partition, null, bucket, password);
