        this(totalBodyLength, partition, startSequenceNumber, endSequenceNumber, flags, bucket, bucket, password);
    }

    public SnapshotMarkerMessage(int totalBodyLength, short partition, long startSequenceNumber, long endSequenceNumber,
                                 int flags, String bucket, String username, String password) {
        super(totalBodyLength, partition, null, bucket, username, password);
