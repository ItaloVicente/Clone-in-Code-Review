    @Deprecated
    public StreamEndMessage(int totalBodyLength, short partition, final Reason reason, String bucket, String password) {
        this(totalBodyLength, partition, reason, bucket, bucket, password);
    }

    public StreamEndMessage(int totalBodyLength, short partition, final Reason reason, final String bucket, final String username, final String password) {
        super(totalBodyLength, partition, null, bucket, username, password);
